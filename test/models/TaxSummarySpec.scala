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

package models

import play.api.libs.json.Json
import util.{BaseSpec, Fixtures}


class TaxSummarySpec extends BaseSpec {

  "Calling TaxSummary.fromJsonTaxSummaryDetails" should {
    
    "produce a valid TaxSummary object when supplied good json" in {
      
      val ts = TaxSummary.fromJsonTaxSummaryDetails(Json.parse(Fixtures.exampleTaxSummaryDetailsJson))
      ts shouldBe Fixtures.buildTaxSummary
    }
  }

  "Checking marriage allowance status" should {

    "indicate a transferee but not a transferor if the tax code ends in M" in {
      val ts = TaxSummary(Seq("500M"), Nil)
      ts.isMarriageAllowanceTransferee shouldBe true
      ts.isMarriageAllowanceTransferor shouldBe false
      ts.notMarriageAllowanceCustomer shouldBe false
    }

    "indicate a transferor but not a transferee if the tax code ends in N" in {
      val ts = TaxSummary(Seq("500N"), Nil)
      ts.isMarriageAllowanceTransferee shouldBe false
      ts.isMarriageAllowanceTransferor shouldBe true
      ts.notMarriageAllowanceCustomer shouldBe false
    }

    "indicate neither a transferor or a transferee if the tax code does not end in N or M" in {
      val ts = TaxSummary(Seq("500T"), Nil)
      ts.isMarriageAllowanceTransferee shouldBe false
      ts.isMarriageAllowanceTransferor shouldBe false
      ts.notMarriageAllowanceCustomer shouldBe true
    }
  }

  "Calling TaxSummary.isCompanyBenefitRecipient" should {

    "return true if Iabd type is 30 (Medical Insurance) and iabd type is 31 (Car Benefit) when supplied with good json" in {
      val ts = TaxSummary.fromJsonTaxSummaryDetails(Json.parse(Fixtures.exampleTaxSummaryDetailsJsonBothCompanyBenefits))
      val isCompanyBenefit = ts.isCompanyBenefitRecipient
      isCompanyBenefit shouldBe true
    }

    "return false if no Iabd type is supplied with good json" in {
      val ts = TaxSummary.fromJsonTaxSummaryDetails(Json.parse(Fixtures.exampleTaxSummaryDetailsJson))
      val isCompanyBenefit = ts.isCompanyBenefitRecipient
      isCompanyBenefit shouldBe false
    }

    "return true if there is only one Iabd type equal to 30 (Medical Insurance) when supplied with good json" in {
      val ts = TaxSummary.fromJsonTaxSummaryDetails(Json.parse(Fixtures.exampleTaxSummaryDetailsJsonCompanyBenefitsMedicalOnly))
      val isCompanyBenefit = ts.isCompanyBenefitRecipient
      isCompanyBenefit shouldBe true
    }

    "return true if there is only one Iabd type equal to 31 (Car Benefit) when supplied with good json" in {
      val ts = TaxSummary.fromJsonTaxSummaryDetails(Json.parse(Fixtures.exampleTaxSummaryDetailsJsonCompanyBenefitsCarOnly))
      val isCompanyBenefit = ts.isCompanyBenefitRecipient
      isCompanyBenefit shouldBe true
    }

  }
}
