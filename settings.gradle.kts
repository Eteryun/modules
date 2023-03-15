rootProject.name = "modules"

sequenceOf(
        "core",
        "scale",
        "backtool",
        "stats",
        "skills",
        "boss"
).forEach {
    include("eteryun-$it")
    project(":eteryun-$it").projectDir = file(it)
}
