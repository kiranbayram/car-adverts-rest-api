package model

import play.api.libs.json.Json

object Implicits {
    implicit val carAdvertFormat = Json.format[CarAdvert]
}
