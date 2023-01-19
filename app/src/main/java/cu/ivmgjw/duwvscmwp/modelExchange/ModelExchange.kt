package cu.ivmgjw.duwvscmwp.modelExchange

public data class ModelExchange(
    val base: String,
    val date: String,
    val motd: Motd,
    val rates: Rates,
    val success: Boolean
)