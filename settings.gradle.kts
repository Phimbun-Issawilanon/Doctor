rootProject.name = "Doctor"
include("download")
include("command")
include("executor")
include("command:fakes")
findProject(":command:fakes")?.name = "fakes"
include("models")
include("run")
include("utils")
include("models")
include("sources")
include("utils:routing")
findProject(":utils:routing")?.name = "routing"
include("run:presenters")
findProject(":run:presenters")?.name = "presenters"
include("run:backend")
findProject(":run:backend")?.name = "backend"
include("run:api")
findProject(":run:api")?.name = "api"
include("utils")
include("download:api")
findProject(":download:api")?.name = "api"
include("download:backend")
findProject(":download:backend")?.name = "backend"
include("download:presenters")
findProject(":download:presenters")?.name = "presenters"
include("run:backend:fakes")
findProject(":run:backend:fakes")?.name = "fakes"
include("sources:fakes")
findProject(":sources:fakes")?.name = "fakes"
include("executor:fakes")
findProject(":executor:fakes")?.name = "fakes"
include("utils:files")
findProject(":utils:files")?.name = "files"
include("tester")
include("tester:backend")
findProject(":tester:backend")?.name = "backend"
include("tester:presenters")
findProject(":tester:presenters")?.name = "presenters"
include("tester:api")
findProject(":tester:api")?.name = "api"
