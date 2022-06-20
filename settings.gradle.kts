rootProject.name = "modules"

sequenceOf(
    "core",
    "scale",
).forEach {
    include("eteryun-$it")
    project(":eteryun-$it").projectDir = file(it)
}
