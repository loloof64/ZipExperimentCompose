package models

import java.util.*

data class FileItem(val isFolder: Boolean, val name: String)

/**
 * extensions: List of String. The extensions without the dot prefix.
 */
fun List<FileItem>.filterByExtensions(extensions: List<String>): List<FileItem> {
    return this.filter { currentItem ->
        currentItem.isFolder ||
            extensions.any { currentExtension ->
                currentItem.name.endsWith(".$currentExtension")
            }
    }
}

/**
 * Sorts items by folder type then by name.
 */
fun List<FileItem>.sort(): List<FileItem> {
    return this.sortedWith { item1, item2 ->
        if (item1.isFolder && item1.name == "..") return@sortedWith -1
        if (item2.isFolder && item2.name == "..") return@sortedWith 1
        if (item1.isFolder && !item2.isFolder) return@sortedWith -1
        if (!item1.isFolder && item2.isFolder) return@sortedWith 1
        return@sortedWith item1.name.lowercase(Locale.getDefault()).compareTo(item2.name.lowercase(Locale.getDefault()))
    }
}