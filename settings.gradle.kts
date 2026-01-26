rootProject.name = "endelith"
include("api", "server", "server:log4j2-plugin")
project(":server:log4j2-plugin").projectDir = file("server/log4j2-plugin")
