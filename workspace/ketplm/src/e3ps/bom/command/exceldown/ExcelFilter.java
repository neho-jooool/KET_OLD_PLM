package e3ps.bom.command.exceldown;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ExcelFilter extends FileFilter
{
	private String fileType;
	private String description;

	public ExcelFilter( String fileType, String description )
	{
		this.fileType = fileType;
		this.description = description;
	}
	
	public String getDescription()
	{
		return description + "(*." + fileType + ")";
	}
	
	public boolean accept( File f ) 
	{
		if( f != null ) 
		{
			if( f.isDirectory() ) 
			{
				return true;
			}

			String extension = getExtension( f );
			if( extension != null && getExtension( f ).equals( fileType ) ) 
			{
				return true;
			}
		}
		return false;
	}

	public String getExtension( File f ) 
	{
		if(f != null) 
		{
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if( i > 0 && i < filename.length() - 1 ) 
			{
				return filename.substring( i + 1 ).toLowerCase();
			}
		}
		return null;
	}
}
