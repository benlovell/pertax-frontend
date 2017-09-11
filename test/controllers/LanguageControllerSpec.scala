/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.binders.ContinueUrl
import util.BaseSpec

class LanguageControllerSpec extends BaseSpec {

  trait LocalSetup {
    val c = app.injector.instanceOf[LanguageController]
  }

  "Calling LanguageController.enGb" should {
    "change the language to English and return 303" in new LocalSetup {
      val r = c.enGb(ContinueUrl("/test"))(FakeRequest("GET", ""))
      cookies(r).get("PLAY_LANG").get.value shouldBe "en"
      status(r) shouldBe SEE_OTHER
    }
  }

  "Calling LanguageController.cyGb" should {
    "change the language to Welsh and return 303" in new LocalSetup {
      val r = c.cyGb(ContinueUrl("/test"))(FakeRequest("GET", ""))
      cookies(r).get("PLAY_LANG").get.value shouldBe "cy"
      status(r) shouldBe SEE_OTHER
    }
  }

  "Calling LanguageController.changeLanguageIfRelativeRedirectUrl" should {
    "return 400 when the url is not relative" in new LocalSetup {
      val r = c.changeLanguageIfRelativeRedirectUrl(ContinueUrl("http://test"), "en")(FakeRequest("GET", "/test"))
      status(r) shouldBe BAD_REQUEST
    }

    "return 303 when url is relative" in new LocalSetup {
      val r = c.changeLanguageIfRelativeRedirectUrl(ContinueUrl("/test"), "en")(FakeRequest("GET", "/test"))
      status(r) shouldBe SEE_OTHER
    }
  }
}
