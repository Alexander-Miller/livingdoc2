package org.livingdoc.repositories.file

import org.livingdoc.repositories.Document
import org.livingdoc.repositories.DocumentFormat
import org.livingdoc.repositories.DocumentRepository
import org.livingdoc.repositories.format.HtmlFormat
import org.livingdoc.repositories.format.MarkdownFormat
import java.io.File
import java.nio.file.Paths

class FileRepository(
        private val name: String,
        private val config: FileRepositoryConfig
) : DocumentRepository {

    override fun getDocument(documentIdentifier: String): Document? {
        val file = Paths.get(config.documentRoot, documentIdentifier).toFile()
        return determineFormatType(file)?.parse(file.inputStream())
    }

    private fun determineFormatType(file: File): DocumentFormat? =
            when (file.extension) {
                ".md" -> MarkdownFormat()
                ".html" -> HtmlFormat()
                else -> null
            }

}
