package repository

import awscala._, dynamodbv2._

object DB {

	def init(): Unit = {
		implicit val dynamoDB = DynamoDB.local()

		val tableMeta: TableMeta = dynamoDB.createTable(
		  name = "CarAdverts",
		  hashPK =  "Id" -> AttributeType.Number,
		  rangePK = "Title" -> AttributeType.String,
		  otherAttributes = Seq("Price" -> AttributeType.Number),
		  indexes = Seq(LocalSecondaryIndex(
			name = "CompanyIndex",
			keySchema = Seq(KeySchema("Id", KeyType.Hash), KeySchema("Price", KeyType.Range)),
				projection = Projection(ProjectionType.Include, Seq("Price"))
			))
		  ) 

		/*val table: Table = dynamoDB.table("CarAdverts").get

		table.put(1, "title", "Price" -> 1234)

		val googlers: Seq[Item] = table.scan(Seq("Price" -> cond.gt(0)))

		googlers.foreach(a => println(a))

		table.destroy()*/
	}
	
}
