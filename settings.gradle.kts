rootProject.name = "Doctor"
include("playground")
include("command")
include("executor")
include("fakes")
include("command:fakes")
findProject(":command:fakes")?.name = "fakes"
include("routing-utils")
include("models")
include("assignment")
include("utils")
include("db")
include("models")
include("sources")
include("utils:routing")
findProject(":utils:routing")?.name = "routing"
