package com.hphil.domain

import javax.persistence.JoinTable
import javax.persistence.OneToOne

open class Asset(
        open val hashId: String,
        open val name: String,
        @OneToOne
        @JoinTable
        open val category: Category)