rootProject.name = "modules"

sequenceOf(
    "core",
).forEach {
    include("eteryun-$it")
    project(":eteryun-$it").projectDir = file(it)
}
