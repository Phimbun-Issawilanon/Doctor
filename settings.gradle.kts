rootProject.name = "Doctor"
include("playground")
include("command")
include("executor")
include("fakes")
include("command:fakes")
findProject(":command:fakes")?.name = "fakes"
include("models")
include("assignment")
include("utils")
include("db")
include("models")
include("sources")
include("utils:routing")
findProject(":utils:routing")?.name = "routing"
include("assignment:presenters")
findProject(":assignment:presenters")?.name = "presenters"
include("assignment:backend")
findProject(":assignment:backend")?.name = "backend"
include("assignment:api")
findProject(":assignment:api")?.name = "api"
include("utils")
