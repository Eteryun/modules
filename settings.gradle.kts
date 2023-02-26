rootProject.name = "modules"

sequenceOf(
        "core",
        "scale",
        "backtool",
        "stats"
).forEach {
    include("eteryun-$it")
    project(":eteryun-$it").projectDir = file(it)
}
