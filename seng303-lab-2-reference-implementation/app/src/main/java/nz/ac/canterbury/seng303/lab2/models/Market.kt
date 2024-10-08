package nz.ac.canterbury.seng303.lab2.models

class Market (
    val id: Int,
    val name: String,
    val description: String,
    val openTimes: String,
    val openTimesForCalender: String,
    val location: String,
    val address:String
) : Identifiable {
    override fun getIdentifier(): Int {
        return id
    }

    companion object {

        fun getMarkets(): List<Market> {
            return listOf(
                Market(
                    id = 1,
                    name = "South Christchurch Farmers Market",
                    description = "Premier destination for fresh, especially organic, produce",
                    openTimes = "Every Sunday, 9am to midday",
                    openTimesForCalender = "Sunday, 09:00",
                    location = "66 Colombo St",
                    address = "South Christchurch Library, South Christchurch Farmers' Market, Christchurch"
                ),
                Market(
                    id = 2,
                    name = "Methven Arts & Growers Market",
                    description = "Local farmers selling fresh produce.",
                    openTimes = "Every Saturday, 8 AM - 2 PM",
                    openTimesForCalender = "Saturday, 08:00",
                    location = "34 Methven Chertsey Road, Methven",
                    address = "256 Methven Chertsey Rd, Methven 7782, New Zealand"
                ),
                Market(
                    id = 3,
                    name = "Lincoln Farmers & Craft Market",
                    description = "Local farmers selling fresh produce.",
                    openTimes = "Every Saturday 10am - 1pm",
                    openTimesForCalender = "Saturday, 10:00",
                    location = "Gerald St, Lincoln, Christchurch",
                    address = "Gerald St, Lincoln, Christchurch"
                ),
                Market(
                    id = 4,
                    name = "Cambridge Farmers Market",
                    description = "Local farmers selling fresh produce.",
                    openTimes = "Saturday 8:10am - midday",
                    openTimesForCalender = "Saturday, 08:00",
                    location = "67 Queen St, Leamington, Cambridge",
                    address = "67 Queen St, Leamington, Cambridge"
                )
            )

        }
    }
}