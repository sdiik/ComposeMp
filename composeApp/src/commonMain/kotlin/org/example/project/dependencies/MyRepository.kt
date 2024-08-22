package org.example.project.dependencies

interface MyRepository {
    fun helloWorld(): String
}

class MyRepositoryImp(
    private  val dbClient: DbClient
) : MyRepository {
    override fun helloWorld(): String {
        return "hello World"
    }
}