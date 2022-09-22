package org.empowrco.doctor.utils

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val utilsModule = module {
    singleOf<FileUtil> { RealFileUtil }
}
