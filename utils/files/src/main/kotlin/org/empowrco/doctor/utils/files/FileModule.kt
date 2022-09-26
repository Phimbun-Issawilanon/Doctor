package org.empowrco.doctor.utils.files

import org.koin.dsl.module

val fileUtilsModule = module {
    single<FileUtil> { RealFileUtil }
}
