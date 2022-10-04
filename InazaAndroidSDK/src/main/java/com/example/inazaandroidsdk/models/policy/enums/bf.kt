package models.policy.enums

enum class PaymentFrequency {
    d,
    bd,
    nd,
    wl,
    ml,
    ql,
    yl,
    bwl,
    bml,
    bql,
    byl,
    wf,
    mf,
    qf,
    yf,
    bwf,
    bmf,
    bqf,
    byf
}

//enum class PaymentFrequency(val freq: String) {
//    DAILY("d"),
//    BUSINESS_DAY("d"),
//    NON_BUSINESS_DAY("nd"),
//    LAST_DAY_OF_WEEK("wl"),
//    LAST_DAY_OF_MONTH("ml"),
//    LAST_DAY_OF_QUARTER("ql"),
//    LAST_DAY_OF_YEAR("yl"),
//    LAST_BUSINESS_DAY_OF_WEEK("bwl"),
//    LAST_BUSINESS_DAY_OF_MONTH("bml"),
//    LAST_BUSINESS_DAY_OF_QUARTER("bql"),
//    LAST_BUSINESS_DAY_OF_YEAR("byl"),
//    FIRST_DAY_OF_WEEK("wf"),
//    FIRST_DAY_OF_MONTH("mf"),
//    FIRST_DAY_OF_QUARTER("qf"),
//    FIRST_DAY_OF_YEAR("yf"),
//    FIRST_BUSINESS_DAY_OF_WEEK("bwf"),
//    FIRST_BUSINESS_DAY_OF_MONTH("bmf"),
//    FIRST_BUSINESS_DAY_OF_QUARTER("bqf"),
//    FIRST_BUSINESS_DAY_OF_YEAR("byf")
//}