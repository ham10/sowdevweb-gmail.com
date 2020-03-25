import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Patient entity.
 */
class PatientGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Patient entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all patients")
            .get("/api/patients")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new patient")
            .post("/api/patients")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "id":null
                , "codePatient":"SAMPLE_TEXT"
                , "nomPatient":"SAMPLE_TEXT"
                , "prenom":"SAMPLE_TEXT"
                , "adresse":"SAMPLE_TEXT"
                , "email":"SAMPLE_TEXT"
                , "dateNaissance":"2020-01-01T00:00:00.000Z"
                , "genre":"SAMPLE_TEXT"
                , "photo":null
                , "password":"SAMPLE_TEXT"
                , "telephone":"SAMPLE_TEXT"
                , "numeroPiece":"SAMPLE_TEXT"
                , "codeBarre":"SAMPLE_TEXT"
                , "entreprise":"SAMPLE_TEXT"
                , "ville":"SAMPLE_TEXT"
                , "quartier":"SAMPLE_TEXT"
                , "longitude":"SAMPLE_TEXT"
                , "latitude":"SAMPLE_TEXT"
                , "lieuNaissance":"SAMPLE_TEXT"
                , "fonctionPatient":"SAMPLE_TEXT"
                , "situationSociale":"SAMPLE_TEXT"
                , "solde":null
                , "cartePatient":"SAMPLE_TEXT"
                , "bloque":null
                , "dateValidite":"2020-01-01T00:00:00.000Z"
                , "motifBlocage":"SAMPLE_TEXT"
                , "prenomPerePatient":"SAMPLE_TEXT"
                , "nomMerePatient":"SAMPLE_TEXT"
                , "prenomMerePatient":"SAMPLE_TEXT"
                , "motifAdmission":"SAMPLE_TEXT"
                , "personneAContacter":"SAMPLE_TEXT"
                , "adressePersAContacter":"SAMPLE_TEXT"
                , "telPersAContacter":"SAMPLE_TEXT"
                , "lienParentePersAContacter":"SAMPLE_TEXT"
                , "nomAccompagnant":"SAMPLE_TEXT"
                , "prenomAccompagnant":"SAMPLE_TEXT"
                , "telAccompagnant":"SAMPLE_TEXT"
                , "habitudeDeVie":"SAMPLE_TEXT"
                , "physioPathologique":"SAMPLE_TEXT"
                , "dateCreated":"2020-01-01T00:00:00.000Z"
                , "dateUpdated":"2020-01-01T00:00:00.000Z"
                , "userCreated":null
                , "userUpdated":null
                , "userDeleted":null
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_patient_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created patient")
                .get("${new_patient_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created patient")
            .delete("${new_patient_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
