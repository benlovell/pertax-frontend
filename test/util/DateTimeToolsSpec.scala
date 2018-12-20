/*
 * Copyright 2018 HM Revenue & Customs
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

package util

import org.joda.time.DateTime

class DateTimeToolsSpec extends BaseSpec {

  "Calling asHumanDateFromUnixDate" should {

    "return correctly formatted readable date when provided with a valid date" in {
      DateTimeTools.asHumanDateFromUnixDate("2018-01-01") shouldBe "01 January 2018"
    }

    "return passed date when provided with an invalid date" in {
      DateTimeTools.asHumanDateFromUnixDate("INVALID DATE FORMAT") shouldBe "INVALID DATE FORMAT"
    }
  }

  "Calling showSendTaxReturnByPost" should {

    "return true when the date is before 01-11-<currentyear>" in {
      val testDate = new DateTime(s"${DateTime.now().getYear()}-10-31T23:59:59")
      DateTimeTools.showSendTaxReturnByPost(testDate) shouldBe true
    }

    "return false when the date is after 01-11-<currentyear>" in {
      val testDate = new DateTime(s"${DateTime.now().getYear()}-11-01T00:00:01Z")
      DateTimeTools.showSendTaxReturnByPost(testDate) shouldBe false
    }

    "return true when the date is after 31-01-<nextyear>" in {
      val testDate = new DateTime(s"${DateTime.now().getYear()+1}-02-01T00:00:00Z")
      DateTimeTools.showSendTaxReturnByPost(testDate) shouldBe true
    }

    "return false when the date is before 01-02-<nextyear>" in {
      val testDate = new DateTime(s"${DateTime.now().getYear()+1}-01-31T23:59:59Z")
      DateTimeTools.showSendTaxReturnByPost(testDate) shouldBe false
    }
  }
}
