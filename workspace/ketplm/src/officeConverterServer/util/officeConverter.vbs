Option Explicit
 
'---------------------
' CONFIGURATION START
'---------------------
'Display a summary in a message box when the conversions are complete
Const SUMMARY_DISPLAY = TRUE
Const SUMMARY_TITLE = "Conversion Complete"
 
'File extensions for PDFs
Const PDF_Extension = "pdf"
 
'Results for CheckFile Function
Const CHECKFILE_OK = 0
Const CHECKFILE_FileDoesNotExist = 1
Const CHECKFILE_NotMSOFile = 2
 
'Settings to produce PDFs from the applications
Const EXCEL_PDF = 0
Const EXCEL_QualityStandard = 0
Const WORD_PDF = 17
Const POWERPOINT_PDF = 32
 
'File types returned from OfficeFileType function
Const FILE_TYPE_NotOffice = 0
Const FILE_TYPE_Word = 1
Const FILE_TYPE_Excel = 2
Const FILE_TYPE_PowerPoint = 3
 
'Valid file type lists
Const FILE_TYPE_DELIMITER = "|"
Dim g_strFileTypesWord
g_strFileTypesWord="|DOC|DOCX|"
Dim g_strFileTypesExcel
g_strFileTypesExcel="|XLS|XLSX|"
Dim g_strFileTypesPowerPoint
g_strFileTypesPowerPoint="|PPT|PPTX|"
Dim c
c = 0
Dim rngCol
Dim rngC
Dim totalWidth
totalWidth = 0
'----------------------
' CONFIGURATION FINISH
'----------------------
 
 
'Call the main routine
Main
'--------------------
' MAIN ROUTINE START
'--------------------
Sub Main()
    Dim colArgs, intCounter, objFSO, strFilePath, category
     
    'Get the command line arguments for the script
    ' - Each chould be a file to be processed
    Set colArgs = Wscript.Arguments
    If colArgs.Count > 0 Then
        'For intCounter = 0 to colArgs.Count - 1
            'strFilePath = Wscript.Arguments(intCounter)
			strFilePath = Wscript.Arguments.Item(0)
			category = Wscript.Arguments.Item(1)
             
            'Check we have a valid file and process it
            Select Case CheckFile(strFilePath)
                Case CHECKFILE_OK
                    Select Case OfficeFileType(strFilePath)
                        Case FILE_TYPE_Word
                            SaveWordAsPDF strFilePath
                             
                        Case FILE_TYPE_Excel
                            SaveExcelAsPDF strFilePath,category
                             
                        Case FILE_TYPE_PowerPoint
                            SavePowerPointAsPDF strFilePath
                    End Select
                     
                Case CHECKFILE_FileDoesNotExist
                    'MsgBox """" & strFilePath & """", vbCritical, "File " & intCounter & " does not exist"
					WScript.Echo "ERR " & """" & strFilePath & """", vbCritical, "File " & intCounter & " does not exist"
                    WScript.Quit
                     
                Case CHECKFILE_NotMSOFile
                    'MsgBox """" & strFilePath & """", vbCritical, "File " & intCounter & " is not a valid file type"
					WScript.Echo "ERR " & """" & strFilePath & """", vbCritical, "File " & intCounter & " is not a valid file type"
                    WScript.Quit
            End Select
        'Next
    Else
        'If there's not even one argument/file to process then exit
        'Msgbox "Please pass a file to this script", 48,"No File Provided"
		WScript.Echo "ERR " & "Please pass a file to this script"
        WScript.Quit
    End If
     
     
    'Display an optional summary message
    If SUMMARY_DISPLAY then
        If colArgs.Count > 1 then
            'MsgBox CStr(colArgs.Count) & " files converted.", vbInformation, SUMMARY_TITLE
			WScript.Echo CStr(colArgs.Count) & " files converted.", vbInformation, SUMMARY_TITLE
        Else
            'MsgBox "1 file converted.", vbInformation, SUMMARY_TITLE
			WScript.Echo "1 file converted.", vbInformation, SUMMARY_TITLE
        End If
    End If
End Sub
'---------------------
' MAIN ROUTINE FINISH
'---------------------
 
 
'--------------------
' SUB-ROUTINES START
'--------------------
Sub SaveExcelAsPDF(p_strFilePath, p_category)
    'Save Excel file as a PDF
     
    'Initialise
    Dim objExcel, objWorkbook, xWs, myLastRow, myLastColumn, RTP
	
    Set objExcel = CreateObject("Excel.Application")
	objExcel.ReferenceStyle=-4150
	objExcel.ScreenUpdating = false
	objExcel.DisplayAlerts = false
     
    'Open the file
    Set objWorkbook = objExcel.Workbooks.Open(p_strFilePath)
	
		For Each xWs In objWorkbook.Sheets
			
			
			If xWs.UsedRange.Address = "$A$1" And _
					objExcel.WorksheetFunction.CountA(xWs.UsedRange) = 0 Then
																			'현재시트가 빈 시트라면 수행안함
					'Exit Sub 
			Else
				
				IF xWs.Visible Then '숨김시트는 대상에서 제외
				
					'For Each rngCol In xWs.UsedRange.Columns         '전체영역(rngAll)의 각열(rngCol)을 순환
					'	c = 0
					'	For Each rngC In rngCol.Cells           '각 열내의 각 셀(rngC)을 순환
					'	
					'		If c = 0 Then
					'			totalWidth = totalWidth + rngC.ColumnWidth
					'		End If
					'		c = c + 1 
					'	Next
						
					'Next

					'myLastRow = LastRow(xWs)
					'myLastColumn = LastCol(xWs)
					
					'WScript.Echo xWs.Name & " 의 myLastRow " & myLastRow
					'WScript.Echo xWs.Name & " 의 myLastColumn " & myLastColumn
					totalWidth = 0
					If p_category = "A" Then
					
						'xWs.PageSetup.PaperSize = 8
						xWs.PageSetup.Zoom = False
						xWs.PageSetup.Orientation = 2
						xWs.PageSetup.FitToPagesWide = 1
						xWs.PageSetup.FitToPagesTall = FALSE
						xWs.PageSetup.CenterHorizontally = FALSE
						xWs.PageSetup.CenterVertically = FALSE
						xWs.PageSetup.PrintArea = xWs.UsedRange.Address
						'xWs.Range("a1:"&xWs.Range("a1"). _End(-4121).Address).Select 
						'xWs.PageSetup.PrintArea = xWs.UsedRange.Address(xWs.Range("a1").End(-4162))
						'xWs.PageSetup.PrintArea = xWs.UsedRange.Address(GetUsedRangeIncludingCharts(xWs))
						'WScript.Echo xWs.Name & " 의 myLastRow " & xWs.UsedRange.Address(GetUsedRangeIncludingCharts(xWs))
					END If
					xWs.PageSetup.PrintHeadings = FALSE ' 인쇄>행/열 머리글
					xWs.PageSetup.PrintGridlines = FALSE ' 인쇄>눈금선
					xWs.PageSetup.LeftHeader = "" '머리글 왼쪽
					xWs.PageSetup.CenterHeader = "" '머리글 가운데
					xWs.PageSetup.RightHeader = "" '머리글 오른쪽
					xWs.PageSetup.LeftFooter = "" '바닥글 왼쪽
					xWs.PageSetup.CenterFooter = "" '바닥글 가운데
					xWs.PageSetup.RightFooter = "" '바닥글 오른쪽
					xWs.PageSetup.LeftMargin = objExcel.InchesToPoints(0.0) 	'왼쪽여백
					xWs.PageSetup.RightMargin = objExcel.InchesToPoints(0.0)	'오른쪽여백
					xWs.PageSetup.BottomMargin = objExcel.InchesToPoints(0.0)	'아래쪽여백
					xWs.PageSetup.TopMargin = objExcel.InchesToPoints(0.0)		'위쪽여백
					xWs.PageSetup.HeaderMargin = objExcel.InchesToPoints(0.0)   '머리글 여백
					xWs.PageSetup.FooterMargin = objExcel.InchesToPoints(0.0)   '바득글 여백
					'xWs.PageSetup.PaperSize = 8
					'xWs.PageSetup.Zoom = False
					'xWs.PageSetup.Orientation = 2
					'xWs.PageSetup.FitToPagesWide = 1
					'xWs.PageSetup.FitToPagesTall = FALSE
					'xWs.PageSetup.CenterHorizontally = FALSE
					'xWs.PageSetup.CenterVertically = FALSE
					'xWs.PageSetup.PrintArea = xWs.UsedRange.Address
					'Save the PDF
					'objExcel.ActiveWindow.FreezePanes = FALSE
					xWs.ExportAsFixedFormat EXCEL_PDF, PathOfPDFforExcel(p_strFilePath,xWs.Name), EXCEL_QualityStandard, TRUE, FALSE, , , FALSE
				End If
			End If
		
		Next
    'Save the PDF
    'objWorkbook.ExportAsFixedFormat EXCEL_PDF, PathOfPDF(p_strFilePath), EXCEL_QualityStandard, TRUE, FALSE, , , FALSE
     
    'Close the file and exit the application
    objWorkbook.Close FALSE
    objExcel.Quit
	
	Set objWorkbook = Nothing
    Set objExcel = Nothing
End Sub
 
 
Sub SaveWordAsPDF(p_strFilePath)
    'Save Word file as a PDF
     
    'Initialise
    Dim objWord, objDocument
    Set objWord = CreateObject("Word.Application")
     
    'Open the file
    Set objDocument = objWord.Documents.Open(p_strFilePath)
     
    'Save the PDF
    objDocument.SaveAs PathOfPDF(p_strFilePath), WORD_PDF
 
    'Close the file and exit the application
    objDocument.Close FALSE
    objWord.Quit
End Sub
 
 
Sub SavePowerPointAsPDF(p_strFilePath)
    'Save PowerPoint file as a PDF (slides only)
     
    'Initialise
    Dim objPowerPoint, objSlideDeck
    Set objPowerPoint = CreateObject("PowerPoint.Application")
     
    'PowerPoint errors if it isn't visible :-(
    'objPowerPoint.Visible = TRUE
     
    'Open the file
    Set objSlideDeck = objPowerPoint.Presentations.Open(p_strFilePath, , , FALSE)
     
    'Save the PDF
    objSlideDeck.SaveAs PathOfPDF(p_strFilePath), POWERPOINT_PDF, True
 
    'Close the file and exit the application
    objSlideDeck.Close
    objPowerPoint.Quit
End Sub
'---------------------
' SUB-ROUTINES FINISH
'---------------------
 
 
'-----------------
' FUNCTIONS START
'-----------------
Function CheckFile(p_strFilePath)
    'Check file exists and is an office file (Excel, Word, PowerPoint)
     
    'Initialise
    Dim objFSO
    Set objFSO = CreateObject("Scripting.FileSystemObject")
     
    'Check the file exists and is an office file
    If IsOfficeFile(p_strFilePath) then
        If objFSO.FileExists(p_strFilePath) then
            CheckFile = CHECKFILE_OK
        Else
            CheckFile = CHECKFILE_FileDoesNotExist
        End If
    Else
        CheckFile = CHECKFILE_NotMSOFile
    End If
End Function
 
 
Function OfficeFileType(p_strFilePath)
    'Returns the type of office file, based upon file extension
     
    OfficeFileType = FILE_TYPE_NotOffice
     
    If IsWordFile(p_strFilePath) then
        OfficeFileType = FILE_TYPE_Word
    End If
     
    If IsExcelFile(p_strFilePath) then
        OfficeFileType = FILE_TYPE_Excel
    End If
     
    If IsPowerPointFile(p_strFilePath) then
        OfficeFileType = FILE_TYPE_PowerPoint
    End If
End Function
 
Function IsOfficeFile(p_strFilePath)
    'Returns true if a file is an office file (Excel, Word, PowerPoint)
     
    IsOfficeFile = IsWordFile(p_strFilePath) OR IsExcelFile(p_strFilePath) OR IsPowerPointFile(p_strFilePath)
End Function
 
 
Function IsWordFile(p_strFilePath)
    'Returns true if a file is a Word file
     
    IsWordFile = IsInList(GetFileExtension(p_strFilePath), g_strFileTypesWord)
End Function
 
 
Function IsExcelFile(p_strFilePath)
'Returns true if a file is an Excel file
 
    IsExcelFile = IsInList(GetFileExtension(p_strFilePath), g_strFileTypesExcel)
End Function
 
 
Function IsPowerPointFile(p_strFilePath)
'Returns true if a file is a PowerPoint file
 
    IsPowerPointFile = IsInList(GetFileExtension(p_strFilePath), g_strFileTypesPowerPoint)
End Function
 
 
Function IsInList(p_strSearchFor, p_strSearchIn)
    'Search a delimited list for a text string and return true if it's found
     
    Dim intResult
     
    intResult = InStr(1, p_strSearchIn, FILE_TYPE_DELIMITER & p_strSearchFor & FILE_TYPE_DELIMITER, vbTextCompare)
     
    If IsNull(intResult) then
        IsInList = FALSE
    Else
        If intResult = 0 then
            IsInList = FALSE
        Else
            IsInList = TRUE
        End If
    End If
End Function
 
 
Function GetFileExtension(p_strFilePath)
    'Return the file extension from a file path
     
    Dim objFSO
    Set objFSO = CreateObject("Scripting.FileSystemObject")
    GetFileExtension = objFSO.GetExtensionName(p_strFilePath)
End Function
 
 
Function PathOfPDF(p_strOriginalFilePath)
    'Return the path for the PDF file
    'The path will be the same as the original file but with a different file extension
     
    Dim objFSO
     
    'Initialise
    Set objFSO = CreateObject("Scripting.FileSystemObject")
     
        'Build the file path
    'Set the directory
    PathOfPDF = objFSO.GetParentFolderName(p_strOriginalFilePath) & "\"
     
    'Add the original file name without the extension
    PathOfPDF = PathOfPDF & Left(objFSO.GetFileName(p_strOriginalFilePath), Len(objFSO.GetFileName(p_strOriginalFilePath)) - Len(objFSO.GetExtensionName(p_strOriginalFilePath)))
     
    'Finally add the the new file extension
    PathOfPDF = PathOfPDF & PDF_Extension
End Function

Function PathOfPDFforExcel(p_strOriginalFilePath, p_sheetName)
    'Return the path for the PDF file
    'The path will be the same as the original file but with a different file extension
     
    Dim objFSO
     
    'Initialise
    Set objFSO = CreateObject("Scripting.FileSystemObject")
     
        'Build the file path
    'Set the directory
    PathOfPDFforExcel = objFSO.GetParentFolderName(p_strOriginalFilePath) & "\" & p_sheetName
End Function

Function GetUsedRangeIncludingCharts(target)
    Dim firstRow
    Dim firstColumn
    Dim lastRow
    Dim lastColumn
    Dim oneChart

    
        firstRow = target.UsedRange.Cells(1).Row
        firstColumn = target.UsedRange.Cells(1).Column
        lastRow = target.UsedRange.Cells(target.UsedRange.Cells.Count).Row
        lastColumn = target.UsedRange(target.UsedRange.Cells.Count).Column

        For Each oneChart In .ChartObjects
            If oneChart.TopLeftCell.Row < firstRow Then _
                firstRow = oneChart.TopLeftCell.Row
            If oneChart.TopLeftCell.Column < firstColumn Then _
                firstColumn = oneChart.TopLeftCell.Column
            If oneChart.BottomRightCell.Row > lastRow Then _
                lastRow = oneChart.BottomRightCell.Row
            If oneChart.BottomRightCell.Column > lastColumn Then _
                lastColumn = oneChart.BottomRightCell.Column
        Next

        Set GetUsedRangeIncludingCharts = .Range(.Cells(firstRow, firstColumn), _
                                                 .Cells(lastRow, lastColumn))
    

End Function

Function LastRow(sh)
    On Error Resume Next
    LastRow = sh.Cells.Find("*",sh.Range("A1"), 2, -4123, 1,2,False).Row
    On Error GoTo 0
End Function


Function LastCol(sh)
    On Error Resume Next
    LastCol = sh.Cells.Find("*", sh.Range("A1"), 2, -4123, 2, 2, False).Column
    On Error GoTo 0
End Function
'------------------
' FUNCTIONS FINISH
'------------------
