package org.livingdoc.repositories.file

//import org.jsoup.Jsoup
//import org.jsoup.nodes.DataNode
//import org.jsoup.nodes.Node
//import org.jsoup.select.Elements
//import org.livingdoc.repositories.DocumentNode
//import java.io.File

//class HtmlParser {
//
//    fun parse(elements: Elements): List<DocumentNode> {
//        return elements.map { element ->
//            when (element.tagName()) {
//                "h1", "h2", "h3", "h4", "h5", "h6" -> {
//                    val depth = element.tagName().substring(1, 2)
//                    DocumentNode.Heading(element.text(), depth.toInt(), parse(element.children()))
//                }
//                "p" ->
//                    DocumentNode.Paragraph(element.text(), parse(element.children()))
//                "ul" ->
//                    DocumentNode.BulletList(children = parse(element.children()))
//                "ol" ->
//                    when (element.attr("type").isNotBlank()) {
//                        true -> DocumentNode.OrderedHtmlList(element.attr("type"), parse(element.children()))
//                        false -> DocumentNode.OrderedHtmlList(children = parse(element.children()))
//                    }
//                "li" -> {
//                    element.textNodes().forEach { println(it) }
//                    DocumentNode.ListItem(children = parse(element.children()))
//                }
//                "title" ->
//                    DocumentNode.Noop(element.tagName(), parse(element.children()))
//                else ->
//                    DocumentNode.Noop(element.tagName(), parse(element.children()))
//            }
//        }
//
//        /*
//        elements.forEach {
//            System.out.println("${it.tagName()} -  ${it.text()}")
//            if (it.children().size > 0) {
//                render(it.children())
//            } else {
//
//            }
//        }
//*/
//        //when (element.tagName()){
////            "h1" -> System.out.println(it.)
//        //          else {}
//        //    }
//
//    }
//}
//
//fun main(args: Array<String>) {
//    val input = File("C:\\_GIT_CLONES\\livingdoc2\\input.html")
//    var doc = Jsoup.parse(input, "UTF-8")
//
//    var allElements = doc.children()
//
//    //allElements.forEach { System.out.println("Name: ${it.tagName()}, Text: ${it.text()}, SIZE: ${it.children()}") }
//
//    //doc.allElements.forEach { System.out.println("-aa->"+it) }
//
//    var htmlParser = HtmlParser()
//    var ast = htmlParser.parse(allElements)
//
//
//    println("START NODES")
//    doc.children().forEach { show(it) }
//    println("END NODES")
//    htmlParser.parse(allElements).forEach {
//        show(it)
//    }
//}
//
//fun show(node: Node, depth: Int = 0) {
//    var prefix = " "
//    (1..depth).forEach { prefix += " " }
//    println("${prefix}${node}")
//    node.childNodes().forEach {
//        show(it, depth + 1)
//    }
//}
//
//fun show(item: DocumentNode, depth: Int = 0) {
//    var prefix = " "
//    (1..depth).forEach { prefix += " " }
//    println("${prefix}${item}")
//    item.children.forEach {
//        show(it, depth + 1)
//    }
//}
