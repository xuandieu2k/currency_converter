package vn.xdeuhug.currency_converter.utils

import android.util.Log
import vn.xdeuhug.currency_converter.R
import vn.xdeuhug.currency_converter.domain.model.Country
import vn.xdeuhug.currency_converter.domain.model.Currency
import java.math.BigDecimal

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
object CurrencyUtils {
    val listCountry = initDataCountry()
    val listCurrency = initCurrency()

    fun getCurrencyByCode(currencyCode: String): Currency? {
        return listCurrency.find { it.code == currencyCode }
    }

    fun getFlag(currencyCode: String): String {
        val countryCode = when (currencyCode) {
            "AED" -> "AE"
            "AFN" -> "AF"
            "ALL" -> "AL"
            "AMD" -> "AM"
            "ANG" -> "NL" // Netherlands Antillean Guilder
            "AOA" -> "AO"
            "ARS" -> "AR"
            "AUD" -> "AU"
            "AWG" -> "AW"
            "AZN" -> "AZ"
            "BAM" -> "BA"
            "BBD" -> "BB"
            "BDT" -> "BD"
            "BGN" -> "BG"
            "BHD" -> "BH"
            "BIF" -> "BI"
            "BMD" -> "BM"
            "BND" -> "BN"
            "BOB" -> "BO"
            "BRL" -> "BR"
            "BSD" -> "BS"
            "BTN" -> "BT"
            "BWP" -> "BW"
            "BYN" -> "BY"
            "BZD" -> "BZ"
            "CAD" -> "CA"
            "CDF" -> "CD"
            "CHF" -> "CH"
            "CLP" -> "CL"
            "CNY" -> "CN"
            "COP" -> "CO"
            "CRC" -> "CR"
            "CUP" -> "CU"
            "CVE" -> "CV"
            "CZK" -> "CZ"
            "DJF" -> "DJ"
            "DKK" -> "DK"
            "DOP" -> "DO"
            "DZD" -> "DZ"
            "EGP" -> "EG"
            "ERN" -> "ER"
            "ETB" -> "ET"
            "EUR" -> "EU"
            "FJD" -> "FJ"
            "FKP" -> "FK"
            "FOK" -> "FO"
            "GBP" -> "GB"
            "GEL" -> "GE"
            "GGP" -> "GG"
            "GHS" -> "GH"
            "GIP" -> "GI"
            "GMD" -> "GM"
            "GNF" -> "GN"
            "GTQ" -> "GT"
            "GYD" -> "GY"
            "HKD" -> "HK"
            "HNL" -> "HN"
            "HRK" -> "HR"
            "HTG" -> "HT"
            "HUF" -> "HU"
            "IDR" -> "ID"
            "ILS" -> "IL"
            "IMP" -> "IM"
            "INR" -> "IN"
            "IQD" -> "IQ"
            "IRR" -> "IR"
            "ISK" -> "IS"
            "JMD" -> "JM"
            "JOD" -> "JO"
            "JPY" -> "JP"
            "KES" -> "KE"
            "KGS" -> "KG"
            "KHR" -> "KH"
            "KID" -> "KI"
            "KMF" -> "KM"
            "KRW" -> "KR"
            "KWD" -> "KW"
            "KYD" -> "KY"
            "KZT" -> "KZ"
            "KPW" -> "KP"
            "LAK" -> "LA"
            "LBP" -> "LB"
            "LKR" -> "LK"
            "LRD" -> "LR"
            "LSL" -> "LS"
            "LYD" -> "LY"
            "MAD" -> "MA"
            "MDL" -> "MD"
            "MGA" -> "MG"
            "MKD" -> "MK"
            "MMK" -> "MM"
            "MNT" -> "MN"
            "MOP" -> "MO"
            "MRU" -> "MR"
            "MUR" -> "MU"
            "MVR" -> "MV"
            "MWK" -> "MW"
            "MXN" -> "MX"
            "MYR" -> "MY"
            "MZN" -> "MZ"
            "NAD" -> "NA"
            "NGN" -> "NG"
            "NIO" -> "NI"
            "NOK" -> "NO"
            "NPR" -> "NP"
            "NZD" -> "NZ"
            "OMR" -> "OM"
            "PAB" -> "PA"
            "PEN" -> "PE"
            "PGK" -> "PG"
            "PHP" -> "PH"
            "PKR" -> "PK"
            "PLN" -> "PL"
            "PYG" -> "PY"
            "QAR" -> "QA"
            "RON" -> "RO"
            "RSD" -> "RS"
            "RUB" -> "RU"
            "RWF" -> "RW"
            "SAR" -> "SA"
            "SBD" -> "SB"
            "SCR" -> "SC"
            "SDG" -> "SD"
            "SEK" -> "SE"
            "SGD" -> "SG"
            "SHP" -> "SH"
            "SLL" -> "SL"
            "SOS" -> "SO"
            "SRD" -> "SR"
            "SSP" -> "SS"
            "STN" -> "ST"
            "SYP" -> "SY"
            "SZL" -> "SZ"
            "THB" -> "TH"
            "TJS" -> "TJ"
            "TMT" -> "TM"
            "TND" -> "TN"
            "TOP" -> "TO"
            "TRY" -> "TR"
            "TTD" -> "TT"
            "TVD" -> "TV"
            "TWD" -> "TW"
            "TZS" -> "TZ"
            "UAH" -> "UA"
            "UGX" -> "UG"
            "USD" -> "US"
            "UYU" -> "UY"
            "UZS" -> "UZ"
            "VES" -> "VE"
            "VND" -> "VN"
            "VUV" -> "VU"
            "WST" -> "WS"
            "XAF" -> "CM" // Central African CFA Franc, often associated with multiple countries
            "XCD" -> "AG" // East Caribbean Dollar, used in multiple countries
            "XOF" -> "BJ" // West African CFA Franc, associated with multiple countries
            "XPF" -> "PF" // CFP Franc
            "YER" -> "YE"
            "ZAR" -> "ZA"
            "ZMW" -> "ZM"
            "ZWL" -> "ZW"
            else -> ""
        }
        return countryCode
    }

    fun mapCurrencyToFlag(currencyCode: String): Int? {
        return initDataCountry().find {
            it.code == getFlag(
                currencyCode
            )
        }?.flag
    }

    private fun initDataCountry(): List<Country> {
        return listOf(
            Country(code = "AF", country = "Afghanistan", flag = R.drawable.af),
            Country(code = "AL", country = "Albania", flag = R.drawable.al),
            Country(code = "DZ", country = "Algeria", flag = R.drawable.dz),
            Country(code = "AD", country = "Andorra", flag = R.drawable.ad),
            Country(code = "AO", country = "Angola", flag = R.drawable.ao),
            Country(code = "AR", country = "Argentina", flag = R.drawable.ar),
            Country(code = "AM", country = "Armenia", flag = R.drawable.am),
            Country(code = "AW", country = "Aruba", flag = R.drawable.aw),
            Country(code = "AU", country = "Australia", flag = R.drawable.au),
            Country(code = "AT", country = "Austria", flag = R.drawable.at),
            Country(code = "AZ", country = "Azerbaijan", flag = R.drawable.az),
            Country(code = "BS", country = "Bahamas", flag = R.drawable.bs),
            Country(code = "BH", country = "Bahrain", flag = R.drawable.bh),
            Country(code = "BD", country = "Bangladesh", flag = R.drawable.bd),
            Country(code = "BB", country = "Barbados", flag = R.drawable.bb),
            Country(code = "BY", country = "Belarus", flag = R.drawable.by),
            Country(code = "BE", country = "Belgium", flag = R.drawable.be),
            Country(code = "BZ", country = "Belize", flag = R.drawable.bz),
            Country(code = "BJ", country = "Benin", flag = R.drawable.bj),
            Country(code = "BM", country = "Bermuda", flag = R.drawable.bm),
            Country(code = "BT", country = "Bhutan", flag = R.drawable.bt),
            Country(code = "BO", country = "Bolivia", flag = R.drawable.bo),
            Country(code = "BA", country = "Bosnia and Herzegovina", flag = R.drawable.ba),
            Country(code = "BW", country = "Botswana", flag = R.drawable.bw),
            Country(code = "BR", country = "Brazil", flag = R.drawable.br),
            Country(code = "BN", country = "Brunei", flag = R.drawable.bn),
            Country(code = "BG", country = "Bulgaria", flag = R.drawable.bg),
            Country(code = "BF", country = "Burkina Faso", flag = R.drawable.bf),
            Country(code = "BI", country = "Burundi", flag = R.drawable.bi),
            Country(code = "CV", country = "Cabo Verde", flag = R.drawable.cv),
            Country(code = "KH", country = "Cambodia", flag = R.drawable.kh),
            Country(code = "CM", country = "Cameroon", flag = R.drawable.cm),
            Country(code = "CA", country = "Canada", flag = R.drawable.ca),
            Country(code = "CF", country = "Central African Republic", flag = R.drawable.cf),
            Country(code = "TD", country = "Chad", flag = R.drawable.td),
            Country(code = "CL", country = "Chile", flag = R.drawable.cl),
            Country(code = "CN", country = "China", flag = R.drawable.cn),
            Country(code = "KY", country = "Cayman Islands", flag = R.drawable.ky),
            Country(code = "CO", country = "Colombia", flag = R.drawable.co),
            Country(code = "KM", country = "Comoros", flag = R.drawable.km),
            Country(code = "CG", country = "Congo", flag = R.drawable.cg),
            Country(code = "CD", country = "Congo (DRC)", flag = R.drawable.cd),
            Country(code = "CR", country = "Costa Rica", flag = R.drawable.cr),
            Country(code = "HR", country = "Croatia", flag = R.drawable.hr),
            Country(code = "CU", country = "Cuba", flag = R.drawable.cu),
            Country(code = "CY", country = "Cyprus", flag = R.drawable.cy),
            Country(code = "CZ", country = "Czech Republic", flag = R.drawable.cz),
            Country(code = "DK", country = "Denmark", flag = R.drawable.dk),
            Country(code = "DJ", country = "Djibouti", flag = R.drawable.dj),
            Country(code = "DM", country = "Dominica", flag = R.drawable.dm),
            Country(code = "DO", country = "Dominican Republic", flag = R.drawable.resource_do),
            Country(code = "EC", country = "Ecuador", flag = R.drawable.ec),
            Country(code = "EG", country = "Egypt", flag = R.drawable.eg),
            Country(code = "SV", country = "El Salvador", flag = R.drawable.sv),
            Country(code = "GQ", country = "Equatorial Guinea", flag = R.drawable.gq),
            Country(code = "ER", country = "Eritrea", flag = R.drawable.er),
            Country(code = "EE", country = "Estonia", flag = R.drawable.ee),
            Country(code = "SZ", country = "Eswatini", flag = R.drawable.sz),
            Country(code = "ET", country = "Ethiopia", flag = R.drawable.et),
            Country(code = "EU", country = "Europe", flag = R.drawable.eu),
            Country(code = "FK", country = "Falkland Islands", flag = R.drawable.fk),
            Country(code = "FO", country = "Faroe Islands", flag = R.drawable.fo),
            Country(code = "FJ", country = "Fiji", flag = R.drawable.fj),
            Country(code = "FI", country = "Finland", flag = R.drawable.fi),
            Country(code = "FR", country = "France", flag = R.drawable.fr),
            Country(code = "GA", country = "Gabon", flag = R.drawable.ga),
            Country(code = "GM", country = "Gambia", flag = R.drawable.gm),
            Country(code = "GE", country = "Georgia", flag = R.drawable.ge),
            Country(code = "DE", country = "Germany", flag = R.drawable.de),
            Country(code = "GH", country = "Ghana", flag = R.drawable.gh),
            Country(code = "GI", country = "Gibraltar", flag = R.drawable.gi),
            Country(code = "GR", country = "Greece", flag = R.drawable.gr),
            Country(code = "GD", country = "Grenada", flag = R.drawable.gd),
            Country(code = "GT", country = "Guatemala", flag = R.drawable.gt),
            Country(code = "GG", country = "Guernsey", flag = R.drawable.gg),
            Country(code = "GN", country = "Guinea", flag = R.drawable.gn),
            Country(code = "GW", country = "Guinea-Bissau", flag = R.drawable.gw),
            Country(code = "GY", country = "Guyana", flag = R.drawable.gy),
            Country(code = "HT", country = "Haiti", flag = R.drawable.ht),
            Country(code = "HN", country = "Honduras", flag = R.drawable.hn),
            Country(code = "HK", country = "Hong Kong", flag = R.drawable.hk),
            Country(code = "HU", country = "Hungary", flag = R.drawable.hu),
            Country(code = "IS", country = "Iceland", flag = R.drawable.`is`),
            Country(code = "IN", country = "India", flag = R.drawable.`in`),
            Country(code = "ID", country = "Indonesia", flag = R.drawable.id),
            Country(code = "IR", country = "Iran", flag = R.drawable.ir),
            Country(code = "IQ", country = "Iraq", flag = R.drawable.iq),
            Country(code = "IE", country = "Ireland", flag = R.drawable.ie),
            Country(code = "IM", country = "Isle of Man", flag = R.drawable.im),
            Country(code = "IL", country = "Israel", flag = R.drawable.il),
            Country(code = "IT", country = "Italy", flag = R.drawable.it),
            Country(code = "CI", country = "Ivory Coast", flag = R.drawable.ci),
            Country(code = "JM", country = "Jamaica", flag = R.drawable.jm),
            Country(code = "JP", country = "Japan", flag = R.drawable.jp),
            Country(code = "JO", country = "Jordan", flag = R.drawable.jo),
            Country(code = "KZ", country = "Kazakhstan", flag = R.drawable.kz),
            Country(code = "KE", country = "Kenya", flag = R.drawable.ke),
            Country(code = "KI", country = "Kiribati", flag = R.drawable.ki),
            Country(code = "KP", country = "North Korea", flag = R.drawable.kp),
            Country(code = "KR", country = "South Korea", flag = R.drawable.kr),
            Country(code = "KW", country = "Kuwait", flag = R.drawable.kw),
            Country(code = "KG", country = "Kyrgyzstan", flag = R.drawable.kg),
            Country(code = "LA", country = "Laos", flag = R.drawable.la),
            Country(code = "LV", country = "Latvia", flag = R.drawable.lv),
            Country(code = "LB", country = "Lebanon", flag = R.drawable.lb),
            Country(code = "LS", country = "Lesotho", flag = R.drawable.ls),
            Country(code = "LR", country = "Liberia", flag = R.drawable.lr),
            Country(code = "LY", country = "Libya", flag = R.drawable.ly),
            Country(code = "LI", country = "Liechtenstein", flag = R.drawable.li),
            Country(code = "LT", country = "Lithuania", flag = R.drawable.lt),
            Country(code = "LU", country = "Luxembourg", flag = R.drawable.lu),
            Country(code = "MO", country = "Macau", flag = R.drawable.mo),
            Country(code = "MG", country = "Madagascar", flag = R.drawable.mg),
            Country(code = "MW", country = "Malawi", flag = R.drawable.mw),
            Country(code = "MY", country = "Malaysia", flag = R.drawable.my),
            Country(code = "MV", country = "Maldives", flag = R.drawable.mv),
            Country(code = "ML", country = "Mali", flag = R.drawable.ml),
            Country(code = "MT", country = "Malta", flag = R.drawable.mt),
            Country(code = "MH", country = "Marshall Islands", flag = R.drawable.mh),
            Country(code = "MR", country = "Mauritania", flag = R.drawable.mr),
            Country(code = "MU", country = "Mauritius", flag = R.drawable.mu),
            Country(code = "MX", country = "Mexico", flag = R.drawable.mx),
            Country(code = "FM", country = "Micronesia", flag = R.drawable.fm),
            Country(code = "MD", country = "Moldova", flag = R.drawable.md),
            Country(code = "MC", country = "Monaco", flag = R.drawable.mc),
            Country(code = "MN", country = "Mongolia", flag = R.drawable.mn),
            Country(code = "ME", country = "Montenegro", flag = R.drawable.me),
            Country(code = "MA", country = "Morocco", flag = R.drawable.ma),
            Country(code = "MZ", country = "Mozambique", flag = R.drawable.mz),
            Country(code = "MM", country = "Myanmar", flag = R.drawable.mm),
            Country(code = "NA", country = "Namibia", flag = R.drawable.na),
            Country(code = "NR", country = "Nauru", flag = R.drawable.nr),
            Country(code = "NP", country = "Nepal", flag = R.drawable.np),
            Country(code = "NL", country = "Netherlands", flag = R.drawable.nl),
            Country(code = "NZ", country = "New Zealand", flag = R.drawable.nz),
            Country(code = "NI", country = "Nicaragua", flag = R.drawable.ni),
            Country(code = "NE", country = "Niger", flag = R.drawable.ne),
            Country(code = "NG", country = "Nigeria", flag = R.drawable.ng),
            Country(code = "MK", country = "North Macedonia", flag = R.drawable.mk),
            Country(code = "NO", country = "Norway", flag = R.drawable.no),
            Country(code = "OM", country = "Oman", flag = R.drawable.om),
            Country(code = "PK", country = "Pakistan", flag = R.drawable.pk),
            Country(code = "PW", country = "Palau", flag = R.drawable.pw),
            Country(code = "PA", country = "Panama", flag = R.drawable.pa),
            Country(code = "PG", country = "Papua New Guinea", flag = R.drawable.pg),
            Country(code = "PY", country = "Paraguay", flag = R.drawable.py),
            Country(code = "PE", country = "Peru", flag = R.drawable.pe),
            Country(code = "PH", country = "Philippines", flag = R.drawable.ph),
            Country(code = "PL", country = "Poland", flag = R.drawable.pl),
            Country(code = "PT", country = "Portugal", flag = R.drawable.pt),
            Country(code = "QA", country = "Qatar", flag = R.drawable.qa),
            Country(code = "RO", country = "Romania", flag = R.drawable.ro),
            Country(code = "RU", country = "Russia", flag = R.drawable.ru),
            Country(code = "RW", country = "Rwanda", flag = R.drawable.rw),
            Country(code = "SH", country = "Saint Helena", flag = R.drawable.sh_ta),
            Country(code = "WS", country = "Samoa", flag = R.drawable.ws),
            Country(code = "SM", country = "San Marino", flag = R.drawable.sm),
            Country(code = "ST", country = "São Tomé and Príncipe", flag = R.drawable.st),
            Country(code = "SA", country = "Saudi Arabia", flag = R.drawable.sa),
            Country(code = "SN", country = "Senegal", flag = R.drawable.sn),
            Country(code = "RS", country = "Serbia", flag = R.drawable.rs),
            Country(code = "SC", country = "Seychelles", flag = R.drawable.sc),
            Country(code = "SL", country = "Sierra Leone", flag = R.drawable.sl),
            Country(code = "SG", country = "Singapore", flag = R.drawable.sg),
            Country(code = "SK", country = "Slovakia", flag = R.drawable.sk),
            Country(code = "SI", country = "Slovenia", flag = R.drawable.si),
            Country(code = "SB", country = "Solomon Islands", flag = R.drawable.sb),
            Country(code = "SO", country = "Somalia", flag = R.drawable.so),
            Country(code = "ZA", country = "South Africa", flag = R.drawable.za),
            Country(code = "SS", country = "South Sudan", flag = R.drawable.ss),
            Country(code = "ES", country = "Spain", flag = R.drawable.es),
            Country(code = "LK", country = "Sri Lanka", flag = R.drawable.lk),
            Country(code = "SD", country = "Sudan", flag = R.drawable.sd),
            Country(code = "SR", country = "Suriname", flag = R.drawable.sr),
            Country(code = "SE", country = "Sweden", flag = R.drawable.se),
            Country(code = "CH", country = "Switzerland", flag = R.drawable.ch),
            Country(code = "SY", country = "Syria", flag = R.drawable.sy),
            Country(code = "TW", country = "Taiwan", flag = R.drawable.tw),
            Country(code = "TJ", country = "Tajikistan", flag = R.drawable.tj),
            Country(code = "TZ", country = "Tanzania", flag = R.drawable.tz),
            Country(code = "TH", country = "Thailand", flag = R.drawable.th),
            Country(code = "TL", country = "Timor-Leste", flag = R.drawable.tl),
            Country(code = "TG", country = "Togo", flag = R.drawable.tg),
            Country(code = "TO", country = "Tonga", flag = R.drawable.to),
            Country(code = "TT", country = "Trinidad and Tobago", flag = R.drawable.tt),
            Country(code = "TN", country = "Tunisia", flag = R.drawable.tn),
            Country(code = "TR", country = "Turkey", flag = R.drawable.tr),
            Country(code = "TM", country = "Turkmenistan", flag = R.drawable.tm),
            Country(code = "TV", country = "Tuvalu", flag = R.drawable.tv),
            Country(code = "UG", country = "Uganda", flag = R.drawable.ug),
            Country(code = "UA", country = "Ukraine", flag = R.drawable.ua),
            Country(code = "AE", country = "United Arab Emirates", flag = R.drawable.ae),
            Country(code = "GB", country = "United Kingdom", flag = R.drawable.gb),
            Country(code = "US", country = "United States", flag = R.drawable.us),
            Country(code = "UY", country = "Uruguay", flag = R.drawable.uy),
            Country(code = "UZ", country = "Uzbekistan", flag = R.drawable.uz),
            Country(code = "VU", country = "Vanuatu", flag = R.drawable.vu),
            Country(code = "VE", country = "Venezuela", flag = R.drawable.ve),
            Country(code = "VN", country = "Vietnam", flag = R.drawable.vn),
            Country(code = "YE", country = "Yemen", flag = R.drawable.ye),
            Country(code = "ZM", country = "Zambia", flag = R.drawable.zm),
            Country(code = "ZW", country = "Zimbabwe", flag = R.drawable.zw)
        )
    }

    private fun initCurrency(): List<Currency> {
        val currencies = listOf(
            Currency("AED", "United Arab Emirates Dirham", "United Arab Emirates", BigDecimal.ZERO),
            Currency("AFN", "Afghan Afghani", "Afghanistan", BigDecimal.ZERO),
            Currency("ALL", "Albanian Lek", "Albania", BigDecimal.ZERO),
            Currency("AMD", "Armenian Dram", "Armenia", BigDecimal.ZERO),
            Currency("ANG", "Netherlands Antillean Guilder", "Netherlands Antilles", BigDecimal.ZERO),
            Currency("AOA", "Angolan Kwanza", "Angola", BigDecimal.ZERO),
            Currency("ARS", "Argentine Peso", "Argentina", BigDecimal.ZERO),
            Currency("AUD", "Australian Dollar", "Australia", BigDecimal.ZERO),
            Currency("AWG", "Aruban Florin", "Aruba", BigDecimal.ZERO),
            Currency("AZN", "Azerbaijani Manat", "Azerbaijan", BigDecimal.ZERO),
            Currency("BAM", "Bosnia-Herzegovina Convertible Mark", "Bosnia and Herzegovina", BigDecimal.ZERO),
            Currency("BBD", "Barbadian Dollar", "Barbados", BigDecimal.ZERO),
            Currency("BDT", "Bangladeshi Taka", "Bangladesh", BigDecimal.ZERO),
            Currency("BGN", "Bulgarian Lev", "Bulgaria", BigDecimal.ZERO),
            Currency("BHD", "Bahraini Dinar", "Bahrain", BigDecimal.ZERO),
            Currency("BIF", "Burundian Franc", "Burundi", BigDecimal.ZERO),
            Currency("BMD", "Bermudian Dollar", "Bermuda", BigDecimal.ZERO),
            Currency("BND", "Brunei Dollar", "Brunei", BigDecimal.ZERO),
            Currency("BOB", "Bolivian Boliviano", "Bolivia", BigDecimal.ZERO),
            Currency("BRL", "Brazilian Real", "Brazil", BigDecimal.ZERO),
            Currency("BSD", "Bahamian Dollar", "Bahamas", BigDecimal.ZERO),
            Currency("BTN", "Bhutanese Ngultrum", "Bhutan", BigDecimal.ZERO),
            Currency("BWP", "Botswana Pula", "Botswana", BigDecimal.ZERO),
            Currency("BYN", "Belarusian Ruble", "Belarus", BigDecimal.ZERO),
            Currency("BZD", "Belize Dollar", "Belize", BigDecimal.ZERO),
            Currency("CAD", "Canadian Dollar", "Canada", BigDecimal.ZERO),
            Currency("CDF", "Congolese Franc", "Democratic Republic of the Congo", BigDecimal.ZERO),
            Currency("CHF", "Swiss Franc", "Switzerland", BigDecimal.ZERO),
            Currency("CLP", "Chilean Peso", "Chile", BigDecimal.ZERO),
            Currency("CNY", "Chinese Yuan", "China", BigDecimal.ZERO),
            Currency("COP", "Colombian Peso", "Colombia", BigDecimal.ZERO),
            Currency("CRC", "Costa Rican Colón", "Costa Rica", BigDecimal.ZERO),
            Currency("CUP", "Cuban Peso", "Cuba", BigDecimal.ZERO),
            Currency("CVE", "Cape Verdean Escudo", "Cape Verde", BigDecimal.ZERO),
            Currency("CZK", "Czech Koruna", "Czech Republic", BigDecimal.ZERO),
            Currency("DJF", "Djiboutian Franc", "Djibouti", BigDecimal.ZERO),
            Currency("DKK", "Danish Krone", "Denmark", BigDecimal.ZERO),
            Currency("DOP", "Dominican Peso", "Dominican Republic", BigDecimal.ZERO),
            Currency("DZD", "Algerian Dinar", "Algeria", BigDecimal.ZERO),
            Currency("EGP", "Egyptian Pound", "Egypt", BigDecimal.ZERO),
            Currency("ERN", "Eritrean Nakfa", "Eritrea", BigDecimal.ZERO),
            Currency("ETB", "Ethiopian Birr", "Ethiopia", BigDecimal.ZERO),
            Currency("EUR", "Euro", "European Union", BigDecimal.ZERO),
            Currency("FJD", "Fijian Dollar", "Fiji", BigDecimal.ZERO),
            Currency("FKP", "Falkland Islands Pound", "Falkland Islands", BigDecimal.ZERO),
            Currency("FOK", "Faroese Króna", "Faroe Islands", BigDecimal.ZERO),
            Currency("GBP", "British Pound", "United Kingdom", BigDecimal.ZERO),
            Currency("GEL", "Georgian Lari", "Georgia", BigDecimal.ZERO),
            Currency("GGP", "Guernsey Pound", "Guernsey", BigDecimal.ZERO),
            Currency("GHS", "Ghanaian Cedi", "Ghana", BigDecimal.ZERO),
            Currency("GIP", "Gibraltar Pound", "Gibraltar", BigDecimal.ZERO),
            Currency("GMD", "Gambian Dalasi", "Gambia", BigDecimal.ZERO),
            Currency("GNF", "Guinean Franc", "Guinea", BigDecimal.ZERO),
            Currency("GTQ", "Guatemalan Quetzal", "Guatemala", BigDecimal.ZERO),
            Currency("GYD", "Guyanese Dollar", "Guyana", BigDecimal.ZERO),
            Currency("HKD", "Hong Kong Dollar", "Hong Kong", BigDecimal.ZERO),
            Currency("HNL", "Honduran Lempira", "Honduras", BigDecimal.ZERO),
            Currency("HRK", "Croatian Kuna", "Croatia", BigDecimal.ZERO),
            Currency("HTG", "Haitian Gourde", "Haiti", BigDecimal.ZERO),
            Currency("HUF", "Hungarian Forint", "Hungary", BigDecimal.ZERO),
            Currency("IDR", "Indonesian Rupiah", "Indonesia", BigDecimal.ZERO),
            Currency("ILS", "Israeli New Shekel", "Israel", BigDecimal.ZERO),
            Currency("IMP", "Isle of Man Pound", "Isle of Man", BigDecimal.ZERO),
            Currency("INR", "Indian Rupee", "India", BigDecimal.ZERO),
            Currency("IQD", "Iraqi Dinar", "Iraq", BigDecimal.ZERO),
            Currency("IRR", "Iranian Rial", "Iran", BigDecimal.ZERO),
            Currency("ISK", "Icelandic Króna", "Iceland", BigDecimal.ZERO),
            Currency("JMD", "Jamaican Dollar", "Jamaica", BigDecimal.ZERO),
            Currency("JOD", "Jordanian Dinar", "Jordan", BigDecimal.ZERO),
            Currency("JPY", "Japanese Yen", "Japan", BigDecimal.ZERO),
            Currency("KES", "Kenyan Shilling", "Kenya", BigDecimal.ZERO),
            Currency("KGS", "Kyrgyzstani Som", "Kyrgyzstan", BigDecimal.ZERO),
            Currency("KHR", "Cambodian Riel", "Cambodia", BigDecimal.ZERO),
            Currency("KMF", "Comorian Franc", "Comoros", BigDecimal.ZERO),
            Currency("KPW", "North Korean Won", "North Korea", BigDecimal.ZERO),
            Currency("KRW", "South Korean Won", "South Korea", BigDecimal.ZERO),
            Currency("KWD", "Kuwaiti Dinar", "Kuwait", BigDecimal.ZERO),
            Currency("KYD", "Cayman Islands Dollar", "Cayman Islands", BigDecimal.ZERO),
            Currency("KZT", "Kazakhstani Tenge", "Kazakhstan", BigDecimal.ZERO),
            Currency("LAK", "Lao Kip", "Laos", BigDecimal.ZERO),
            Currency("LBP", "Lebanese Pound", "Lebanon", BigDecimal.ZERO),
            Currency("LKR", "Sri Lankan Rupee", "Sri Lanka", BigDecimal.ZERO),
            Currency("LRD", "Liberian Dollar", "Liberia", BigDecimal.ZERO),
            Currency("LSL", "Lesotho Loti", "Lesotho", BigDecimal.ZERO),
            Currency("LYD", "Libyan Dinar", "Libya", BigDecimal.ZERO),
            Currency("MAD", "Moroccan Dirham", "Morocco", BigDecimal.ZERO),
            Currency("MDL", "Moldovan Leu", "Moldova", BigDecimal.ZERO),
            Currency("MGA", "Malagasy Ariary", "Madagascar", BigDecimal.ZERO),
            Currency("MKD", "Macedonian Denar", "North Macedonia", BigDecimal.ZERO),
            Currency("MMK", "Myanmar Kyat", "Myanmar", BigDecimal.ZERO),
            Currency("MNT", "Mongolian Tögrög", "Mongolia", BigDecimal.ZERO),
            Currency("MOP", "Macanese Pataca", "Macau", BigDecimal.ZERO),
            Currency("MUR", "Mauritian Rupee", "Mauritius", BigDecimal.ZERO),
            Currency("MVR", "Maldivian Rufiyaa", "Maldives", BigDecimal.ZERO),
            Currency("MWK", "Malawian Kwacha", "Malawi", BigDecimal.ZERO),
            Currency("MXN", "Mexican Peso", "Mexico", BigDecimal.ZERO),
            Currency("MYR", "Malaysian Ringgit", "Malaysia", BigDecimal.ZERO),
            Currency("MZN", "Mozambican Metical", "Mozambique", BigDecimal.ZERO),
            Currency("NAD", "Namibian Dollar", "Namibia", BigDecimal.ZERO),
            Currency("NGN", "Nigerian Naira", "Nigeria", BigDecimal.ZERO),
            Currency("NIO", "Nicaraguan Córdoba", "Nicaragua", BigDecimal.ZERO),
            Currency("NOK", "Norwegian Krone", "Norway", BigDecimal.ZERO),
            Currency("NPR", "Nepalese Rupee", "Nepal", BigDecimal.ZERO),
            Currency("NZD", "New Zealand Dollar", "New Zealand", BigDecimal.ZERO),
            Currency("OMR", "Omani Rial", "Oman", BigDecimal.ZERO),
            Currency("PAB", "Panamanian Balboa", "Panama", BigDecimal.ZERO),
            Currency("PEN", "Peruvian Sol", "Peru", BigDecimal.ZERO),
            Currency("PGK", "Papua New Guinean Kina", "Papua New Guinea", BigDecimal.ZERO),
            Currency("PHP", "Philippine Peso", "Philippines", BigDecimal.ZERO),
            Currency("PKR", "Pakistani Rupee", "Pakistan", BigDecimal.ZERO),
            Currency("PLN", "Polish Złoty", "Poland", BigDecimal.ZERO),
            Currency("PYG", "Paraguayan Guaraní", "Paraguay", BigDecimal.ZERO),
            Currency("QAR", "Qatari Riyal", "Qatar", BigDecimal.ZERO),
            Currency("RON", "Romanian Leu", "Romania", BigDecimal.ZERO),
            Currency("RSD", "Serbian Dinar", "Serbia", BigDecimal.ZERO),
            Currency("RUB", "Russian Ruble", "Russia", BigDecimal.ZERO),
            Currency("RWF", "Rwandan Franc", "Rwanda", BigDecimal.ZERO),
            Currency("SAR", "Saudi Riyal", "Saudi Arabia", BigDecimal.ZERO),
            Currency("SBD", "Solomon Islands Dollar", "Solomon Islands", BigDecimal.ZERO),
            Currency("SCR", "Seychellois Rupee", "Seychelles", BigDecimal.ZERO),
            Currency("SDG", "Sudanese Pound", "Sudan", BigDecimal.ZERO),
            Currency("SEK", "Swedish Krona", "Sweden", BigDecimal.ZERO),
            Currency("SGD", "Singapore Dollar", "Singapore", BigDecimal.ZERO),
            Currency("SHP", "Saint Helena Pound", "Saint Helena", BigDecimal.ZERO),
            Currency("SLL", "Sierra Leonean Leone", "Sierra Leone", BigDecimal.ZERO),
            Currency("SOS", "Somali Shilling", "Somalia", BigDecimal.ZERO),
            Currency("SRD", "Surinamese Dollar", "Suriname", BigDecimal.ZERO),
            Currency("SSP", "South Sudanese Pound", "South Sudan", BigDecimal.ZERO),
            Currency("STN", "São Tomé and Príncipe Dobra", "São Tomé and Príncipe", BigDecimal.ZERO),
            Currency("SYP", "Syrian Pound", "Syria", BigDecimal.ZERO),
            Currency("SZL", "Eswatini Lilangeni", "Eswatini", BigDecimal.ZERO),
            Currency("THB", "Thai Baht", "Thailand", BigDecimal.ZERO),
            Currency("TJS", "Tajikistani Somoni", "Tajikistan", BigDecimal.ZERO),
            Currency("TMT", "Turkmenistani Manat", "Turkmenistan", BigDecimal.ZERO),
            Currency("TND", "Tunisian Dinar", "Tunisia", BigDecimal.ZERO),
            Currency("TOP", "Tongan Paʻanga", "Tonga", BigDecimal.ZERO),
            Currency("TRY", "Turkish Lira", "Turkey", BigDecimal.ZERO),
            Currency("TTD", "Trinidad and Tobago Dollar", "Trinidad and Tobago", BigDecimal.ZERO),
            Currency("TWD", "New Taiwan Dollar", "Taiwan", BigDecimal.ZERO),
            Currency("TZS", "Tanzanian Shilling", "Tanzania", BigDecimal.ZERO),
            Currency("UAH", "Ukrainian Hryvnia", "Ukraine", BigDecimal.ZERO),
            Currency("UGX", "Ugandan Shilling", "Uganda", BigDecimal.ZERO),
            Currency("USD", "United States Dollar", "United States", BigDecimal.ZERO),
            Currency("UYU", "Uruguayan Peso", "Uruguay", BigDecimal.ZERO),
            Currency("UZS", "Uzbekistani Soʻm", "Uzbekistan", BigDecimal.ZERO),
            Currency("VES", "Venezuelan Bolívar Soberano", "Venezuela", BigDecimal.ZERO),
            Currency("VND", "Vietnamese Đồng", "Vietnam", BigDecimal.ZERO),
            Currency("VUV", "Vanuatu Vatu", "Vanuatu", BigDecimal.ZERO),
            Currency("WST", "Samoan Tālā", "Samoa", BigDecimal.ZERO),
            Currency("XAF", "Central African CFA Franc", "Central Africa", BigDecimal.ZERO),
//            Currency("XCD", "East Caribbean Dollar", "East Caribbean", BigDecimal.ZERO),
//            Currency("XDR", "Special Drawing Rights", "IMF", BigDecimal.ZERO),
            Currency("XOF", "West African CFA Franc", "West Africa", BigDecimal.ZERO),
//            Currency("XPF", "CFP Franc", "French Polynesia", BigDecimal.ZERO),
            Currency("YER", "Yemeni Rial", "Yemen", BigDecimal.ZERO),
            Currency("ZAR", "South African Rand", "South Africa", BigDecimal.ZERO),
            Currency("ZMW", "Zambian Kwacha", "Zambia", BigDecimal.ZERO),
            Currency("ZWL", "Zimbabwean Dollar", "Zimbabwe", BigDecimal.ZERO)
        )
        Log.d("Data: ", currencies.size.toString())
        return currencies
    }
}