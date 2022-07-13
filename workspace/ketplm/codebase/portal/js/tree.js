
USETEXTLINKS = 1
indexOfEntries = new Array
nEntries = 0
doc = document
browserVersion = 0
selectedTree=0
imageArray = new Array
first = 0
function Tree(treeDescription, cutDesc, hreference, actionURL, target, myID, PId, treePath) { //constructor
	//constant data
	this.desc = treeDescription
	this.cutdesc = cutDesc
	this.hreference = hreference
	this.actionURL = actionURL
	this.target = target
	this.myID = myID
	this.PId = PId
	this.treePath = treePath
	this.id = -1
	this.navObj = 0
	this.iconImg = 0
	this.nodeImg = 0
	this.isLastNode = 0

	//dynamic data
	this.isOpen = true
	this.iconSrc = imageArray["treeopen"];
	this.children = new Array
	this.nChildren = 0

	//methods
	this.initialize = initializeTree
	this.setState = setStateTree
	this.addChild = addChild
	this.createIndex = createEntryIndex
	this.hide = hideTree
	this.display = display
	this.renderOb = drawTree
	this.totalHeight = totalHeight
	this.subEntries = treeSubEntries
	this.outputLink = outputTreeLink
}

function setStateTree(isOpen) {
	var subEntries
	var totalHeight
	var fIt = 0
	var i=0

	if (isOpen == this.isOpen) return

	if (browserVersion == 2) {
		totalHeight = 0
		for (i=0; i < this.nChildren; i++) totalHeight = totalHeight + this.children[i].navObj.clip.height
		subEntries = this.subEntries()
		if (this.isOpen) totalHeight = 0 - totalHeight
		for (fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) indexOfEntries[fIt].navObj.moveBy(0, totalHeight)
	}
	this.isOpen = isOpen
	propagateChangesInState(this)
}

function propagateChangesInState(tree) {
	var i=0

	if (tree.isOpen) {
		if (tree.nodeImg)
			if (tree.isLastNode)
				if(tree.nChildren == 0) tree.nodeImg.src = "/plm/portal/icon/ftv2lastnode.gif";
				else tree.nodeImg.src = imageArray["mlastnode"];
			else
				if(tree.nChildren == 0) tree.nodeImg.src = "/plm/portal/icon/ftv2node.gif";
				else tree.nodeImg.src = imageArray["mnode"];
		if(tree.nChildren ==0) tree.iconImg.src = imageArray["treeclosed"];
		else tree.iconImg.src = imageArray["treeopen"];
		for (i=0; i<tree.nChildren; i++) tree.children[i].display()
	} else {
		if (tree.nodeImg)
			if (tree.isLastNode)
				if(tree.nChildren == 0) tree.nodeImg.src = "/plm/portal/icon/ftv2lastnode.gif";
				else tree.nodeImg.src = "/plm/portal/icon/ftv2plastnode.gif"
			else
				if(tree.nChildren == 0) tree.nodeImg.src = "/plm/portal/icon/ftv2node.gif";
				else tree.nodeImg.src = imageArray["pnode"];
		tree.iconImg.src = imageArray["treeclosed"];
		for (i=0; i<tree.nChildren; i++) tree.children[i].hide()
	}
}

function hideTree() {
	if (browserVersion == 1) {
		if (this.navObj.style.display == "none") return
		this.navObj.style.display = "none"
	} else {
		if (this.navObj.visibility == "hiden") return
		this.navObj.visibility = "hiden"
	}
	this.setState(0)
}

function initializeTree(level, lastNode, leftSide) {
	var j=0
	var i=0
	var numberOfTrees
	var numberOfDocs
	var nc = this.nChildren
	this.createIndex()

	var auxEv = ""
	if (browserVersion > 0) auxEv = "<a href='javascript:clickOnNode("+this.id+")'>"
	else auxEv = "<a>"

	if (level>0)
		if (lastNode) {	//the last 'brother' in the children array
			this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' src='" + imageArray["mlastnode"] + "' border=0></a>")
			leftSide = leftSide + "<img src='/plm/portal/icon/ftv2blank.gif'>"
			this.isLastNode = 1
		} else {
			this.renderOb(leftSide + auxEv + "<img name='nodeIcon" + this.id + "' src='" + imageArray["mnode"] + "' border=0></a>")
			leftSide = leftSide + "<img src='/plm/portal/icon/ftv2vertline.gif'>"
			this.isLastNode = 0
		}
	else this.renderOb("")

	if (nc > 0) {
		level = level + 1
		for (i=0 ; i < this.nChildren; i++) {
			if (i == this.nChildren-1) this.children[i].initialize(level, 1, leftSide)
			else this.children[i].initialize(level, 0, leftSide)
		}
	}
}

function drawTree(leftSide) {
	if (browserVersion == 2) {
		if (!doc.yPos) doc.yPos=8
		doc.write("<layer id='tree" + this.id + "' top=" + (doc.yPos+78) + " visibility=hiden>")
	}

	doc.write("<table border=0 class='main' ")
	if (browserVersion == 1) doc.write(" id='tree" + this.id + "' style='position:block;' ")
	doc.write(" border=0 cellspacing=0 cellpadding=0><tr><td valign=top>" + leftSide)
	this.outputLink()
	doc.write("<img name='treeIcon" + this.id + "' src='" + this.iconSrc+"' border=0></a></td><td width=100 valign=middle nowrap>")
	if (USETEXTLINKS) {
		this.outputLink()
		doc.write(this.cutdesc + "</a></td></table>")
	} else doc.write(this.cutdesc+"</td></table>")
	
	if (browserVersion == 1) {
		this.navObj = doc.all["tree"+this.id]
		this.iconImg = doc.all["treeIcon"+this.id]
		this.nodeImg = doc.all["nodeIcon"+this.id]
	} else if (browserVersion == 2) {
		doc.write("</layer>")
		this.navObj = doc.layers["tree"+this.id]
		this.iconImg = this.navObj.document.images["treeIcon"+this.id]
		this.nodeImg = this.navObj.document.images["nodeIcon"+this.id]
		doc.yPos=doc.yPos+this.navObj.clip.height
	}
}

function addChild(childNode) {
	this.children[this.nChildren] = childNode
	this.nChildren++
	return childNode
}

function treeSubEntries() {
	var i = 0
	var se = this.nChildren

	for (i=0; i < this.nChildren; i++)
		if (this.children[i].children) se = se + this.children[i].subEntries()	//is a tree

	return se
}

// Definition of class Item (a document or link inside a Tree)
// *************************************************************
function Item(itemDescription, cutDesc, itemLink) { // Constructor
	// constant data
	this.desc = itemDescription
	this.cutdesc = cutDesc
	this.link = itemLink
	this.id = -1 //initialized in initalize()
	this.navObj = 0 //initialized in render()
	this.iconImg = 0 //initialized in render()
	this.iconSrc = imageArray["doc"]

	// methods
	this.initialize = initializeItem
	this.createIndex = createEntryIndex
	this.hide = hideItem
	this.display = display
	this.renderOb = drawItem
	this.totalHeight = totalHeight
}

function hideItem() {
	if (browserVersion == 1) {
		if (this.navObj.style.display == "none") return
		this.navObj.style.display = "none"
	} else {
		if (this.navObj.visibility == "hiden") return
		this.navObj.visibility = "hiden"
	}
}

function initializeItem(level, lastNode, leftSide) {
	this.createIndex()

	if (level>0)
		if (lastNode) { //the last 'brother' in the children array
			this.renderOb(leftSide + "<img src='/plm/portal/icon/ftv2lastnode.gif'>")
			leftSide = leftSide + "<img src='/plm/portal/icon/ftv2blank.gif'>"
		} else {
			this.renderOb(leftSide + "<img src='/plm/portal/icon/ftv2node.gif'>")
			leftSide = leftSide + "<img src='/plm/portal/icon/ftv2vertline.gif'>"
		}
	else this.renderOb("")
}

function drawItem(leftSide) {
	if (browserVersion == 2) doc.write("<layer id='item" + this.id + "' top=" + (doc.yPos+78) + " visibility=hiden>")
	doc.write("<table ")
	if (browserVersion == 1) doc.write(" id='item" + this.id + "' style='position:block;' ")
	doc.write(" border=0 cellspacing=0 cellpadding=0><tr><td>")
	doc.write(leftSide)
	doc.write("<a href=" + this.link + "><img id='itemIcon"+this.id+"' src='"+this.iconSrc+"' border=0></a></td><td valign=middle nowrap>")
	if (USETEXTLINKS) doc.write("<a href=" + this.link + " title='" + this.desc + "'>" + this.cutdesc + "</a>")
	else doc.write(this.cutdesc)
	doc.write("</table>")

	if (browserVersion == 1) {
		this.navObj = doc.all["item"+this.id]
		this.iconImg = doc.all["itemIcon"+this.id]
	} else if (browserVersion == 2) {
		doc.write("</layer>")
		this.navObj = doc.layers["item"+this.id]
		this.iconImg = this.navObj.document.images["itemIcon"+this.id]
		doc.yPos=doc.yPos+this.navObj.clip.height
	}
}

// Methods common to both objects (pseudo-inheritance)
// ********************************************************
function display() {
	if (browserVersion == 1) this.navObj.style.display = "block"
	else this.navObj.visibility = "show"
}

function createEntryIndex() {
	this.id = nEntries
	indexOfEntries[nEntries] = this
	nEntries++
}

// total height of subEntries open
function totalHeight() { //used with browserVersion == 2
	var h = this.navObj.clip.height
	var i = 0

	if (this.isOpen)		//is a tree and _is_ open
		for (i=0 ; i < this.nChildren; i++) h = h + this.children[i].totalHeight()	
	return h
}

// Events
// *********************************************************
function clickOnTree(treeId) {
	var clicked = indexOfEntries[treeId]
	forward(clicked)
	if (!clicked.isOpen) { clickOnNode(treeId) }
	return
}

function overOnNode(treeId) {}

function clickOnNode(treeId) {
	var clickedTree = 0
	var state = 0
	clickedTree = indexOfEntries[treeId]
	state = clickedTree.isOpen
	clickedTree.setState(!state) //open<->close
}

function setImageArray(imgType) {
	imageArray["treeopen"]="/plm/portal/icon/ftv2treeopen.gif";
	imageArray["treeclosed"]="/plm/portal/icon/ftv2treeclosed.gif";
	imageArray["doc"]="/plm/portal/icon/ftv2doc.gif";
	imageArray["mlastnode"]="/plm/portal/icon/ftv2mlastnode.gif";
	imageArray["mnode"]="/plm/portal/icon/ftv2mnode.gif";
	imageArray["pnode"]="/plm/portal/icon/ftv2pnode.gif";
}

function initializeDocument() {
	if (doc.all) browserVersion = 1 //IE4
	else 
		if (doc.layers) browserVersion = 2 //NS4
		else browserVersion = 0 //other

	treesTree.initialize(1,2, "")
	treesTree.display()

	if (browserVersion > 0) {
		doc.write("<layer top="+indexOfEntries[nEntries-1].navObj.top+">&nbsp;</layer>")	
		clickOnNode(0)	 	// open tree node	
		clickOnNode(0)
//		for ( i = index+subIndex+1 ; i < nEntries ; i++ ) clickOnNode(i);		// cloase tree node
		
		first = 1
	}
}

// Auxiliary Functions for Tree-Treee backward compatibility
// *********************************************************
function gFld(description, cutdesc, hreference, actionURL, target, myID, PId, treePath) {
	tree = new Tree(description, cutdesc, hreference, actionURL, target, myID, PId, treePath)
	return tree
}

function insFld(parentTree, childTree) { return parentTree.addChild(childTree); }
function insDoc(parentTree, document) { parentTree.addChild(document); }

// 요기야


function outputTreeLink() {
	if (this.hreference) {
		var str = "?";
		if ( this.hreference.indexOf("?") > 0 ) str = "&";
		doc.write("<a href='" + this.hreference + str + "Id="+this.myID+"&PId="+this.PId+"' ")	
		if (browserVersion > 0) doc.write("onClick='javascript:clickOnTree("+this.id+")'")
		doc.write(">")
	} else {
		doc.write("<a href='javascript:clickOnFolder("+this.id+")' title='"+this.desc+"'>")
	}
}
