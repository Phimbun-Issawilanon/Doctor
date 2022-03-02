rootProject.name = "Doctor"
include("playground")
include("command")
include("executor")
include("fakes")
include("command:fakes")
findProject(":command:fakes")?.name = "fakes"
include("routing-utils")
