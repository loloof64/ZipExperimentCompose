actual fun getStartDirectory(): String {
    val context = splitties.init.appCtx
    return context.getApplicationInfo().dataDir
}