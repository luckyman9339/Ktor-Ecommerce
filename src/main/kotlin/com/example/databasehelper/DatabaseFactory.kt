package com.example.databasehelper

import com.example.entities.product.*
import com.example.entities.product.defaultproductcategory.ProductCategoryTable
import com.example.entities.product.defaultproductcategory.ProductSubCategoryTable
import com.example.entities.product.defaultvariant.ProductColorTable
import com.example.entities.product.defaultvariant.ProductSizeTable
import com.example.entities.shop.ShopCategoryTable
import com.example.entities.shop.ShopTable
import com.example.entities.user.UserHasTypeTable
import com.example.entities.user.UserTypeTable
import com.example.entities.user.UserProfileTable
import com.example.entities.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.slf4j.LoggerFactory
import java.net.URI
import javax.sql.DataSource

object DatabaseFactory {
    private val log = LoggerFactory.getLogger(this::class.java)
    fun init() {
        //initDB()
        Database.connect("jdbc:postgresql://ec2-52-204-46-21.compute-1.amazonaws.com:5432/d5e1gb67c6rp9g", driver = "org.postgresql.Driver", user = "gogyrrsqrfjjxj", password = "5026a0cfe9e7e351b1a3cc9c4c63570cca93db006369bd0e31b3103bf3ecab61")
        transaction {
            create(UserTable, UserProfileTable, UserTypeTable, UserHasTypeTable,ShopTable, ShopCategoryTable, ProductCategoryTable, ProductSubCategoryTable, ProductTable, ProductSizeTable, ProductColorTable)
        }
    }

    private fun initDB() {
        // database connection is handled from hikari properties
        val config = HikariConfig("/hikari.properties")
        val dataSource = HikariDataSource(config)
        runFlyway(dataSource)
        Database.connect(dataSource)
    }
    private fun hikari():HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("JDBC_DRIVER")
        config.jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        config.maximumPoolSize = 3
        config.isAutoCommit = true
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    // For heroku deployement
    private fun hikariForHeroku(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = System.getenv("JDBC_DRIVER")
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        val uri = URI(System.getenv("DATABASE_URL"))
        val username = uri.userInfo.split(":").toTypedArray()[0]
        val password = uri.userInfo.split(":").toTypedArray()[1]

        config.jdbcUrl =
            "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"

        config.validate()
        return HikariDataSource(config)
    }


    // database connection for h2
    private fun hikariForH2(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:file:~/documents/db/h2db"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    private fun runFlyway(datasource: DataSource) {
        val flyway = Flyway.configure().dataSource(datasource).load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            log.error("Exception running flyway migration", e)
            throw e
        }
        log.info("Flyway migration has finished")
    }
}