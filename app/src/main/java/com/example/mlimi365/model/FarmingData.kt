package com.example.mlimi365.model

data class CropGuide(
    val id: String,
    val name: String,
    val emoji: String,
    val category: String,
    val plantingSeason: String,
    val plantingGuide: String,
    val spacing: String,
    val fertilizer: String,
    val weeding: String,
    val harvesting: String
)

data class MonthInfo(
    val monthNumber: Int, // 1 to 12
    val monthName: String,
    val periodTitle: String,
    val seasonType: String, // "Dry", "Land Prep", "Planting & Rainy", "Weeding", "Harvest"
    val description: String,
    val activities: List<String>,
    val suitableCrops: List<String>
)

object OfflineFarmingRepository {
    val crops = listOf(
        CropGuide(
            id = "maize",
            name = "Maize",
            emoji = "🌽",
            category = "Cereals",
            plantingSeason = "Nov – Dec (Rainy Season)",
            plantingGuide = "Plant 2-3 seeds per station at a depth of 3-5 cm with the onset of reliable rains.",
            spacing = "75 cm between rows and 25 cm between stations (1 seed per hole) or 90 cm x 50 cm (2 seeds).",
            fertilizer = "Apply basal dressing (NPK / Compound D) at planting (2 bags per acre). Top dress with Urea when maize is knee-high (at 4-6 weeks).",
            weeding = "First weeding at 2-3 weeks after germination. Second weeding before top-dressing / canopy closure.",
            harvesting = "Harvest when cobs turn brown, husks are dry, and grain moisture content is around 20-25% (test hardness with thumbnail)."
        ),
        CropGuide(
            id = "beans",
            name = "Beans",
            emoji = "🫘",
            category = "Legumes",
            plantingSeason = "Nov – Jan (Rainy Season)",
            plantingGuide = "Plant certified disease-free seeds in shallow furrows 3-5 cm deep.",
            spacing = "50 cm between rows and 10 cm between plants.",
            fertilizer = "Usually requires little nitrogen. Apply basal D fertilizer if soils are poor. Inoculation can improve nitrogen fixation.",
            weeding = "Keep weed-free during early vegetative growth (first 4 weeks). Avoid weeding when plants are wet to prevent disease spread.",
            harvesting = "Harvest when pods turn yellow/brown and rattle when shaken. Can be pulled whole and sun-dried."
        ),
        CropGuide(
            id = "groundnuts",
            name = "Groundnuts",
            emoji = "🥜",
            category = "Legumes",
            plantingSeason = "Nov – Dec (Rainy Season)",
            plantingGuide = "Shelled kernels should be treated with fungicides before planting. Plant 1 seed per hole at 4-5 cm depth.",
            spacing = "45 cm between rows and 10-15 cm between plants.",
            fertilizer = "Apply gypsum at flowering for proper pod filling and shell development. Avoid excess nitrogen.",
            weeding = "Weed early before pegging starts. Do not disturb soil or turn branches once pegging and flowering begin.",
            harvesting = "Harvest when leaves turn yellow and inner shells show dark veins. Pull plants and cure in stacks."
        ),
        CropGuide(
            id = "soybeans",
            name = "Soybeans",
            emoji = "🌱",
            category = "Legumes",
            plantingSeason = "Nov – Dec (Rainy Season)",
            plantingGuide = "Plant in well-drained soils at 3 cm depth. Inoculate seeds with Rhizobium inoculant for better yields.",
            spacing = "45-60 cm between rows and 5 cm between plants.",
            fertilizer = "Low nitrogen requirement. Apply single superphosphate (SSP) at planting for root and nodule development.",
            weeding = "Keep weed-free for the first 4-6 weeks until crop canopy covers the ground.",
            harvesting = "Harvest when 90% of pods have turned brown and seeds rattle inside. Cut at base or pull."
        ),
        CropGuide(
            id = "cassava",
            name = "Cassava",
            emoji = "🥔",
            category = "Tubers",
            plantingSeason = "Nov – Jan (Rainy Season)",
            plantingGuide = "Use healthy disease-free stem cuttings (20-30 cm long). Plant horizontally or slanted with 2/3 underground.",
            spacing = "1m x 1m spacing for optimal root expansion.",
            fertilizer = "Farmyard manure or NPK compound applied 4-6 weeks after sprouting if soils are depleted.",
            weeding = "Frequent weeding required during the first 3-4 months until the canopy closes.",
            harvesting = "Harvest from 8 to 12 months depending on variety by carefully digging up roots."
        ),
        CropGuide(
            id = "sweet_potatoes",
            name = "Sweet Potatoes",
            emoji = "🍠",
            category = "Tubers",
            plantingSeason = "Nov – Feb (Rainy Season)",
            plantingGuide = "Use healthy vine cuttings (30 cm long). Plant on ridges with 2/3 of the vine buried.",
            spacing = "90 cm between ridges and 30 cm between plants.",
            fertilizer = "Potassium-rich fertilizers or compost manure help root tuber enlargement.",
            weeding = "Earthing up and weeding during the first 6 weeks before vines cover the ridges.",
            harvesting = "Harvest when leaves begin to yellow (3.5 to 5 months after planting)."
        ),
        CropGuide(
            id = "tomatoes",
            name = "Tomatoes",
            emoji = "🍅",
            category = "Vegetables",
            plantingSeason = "All year round (Rainy with shelter / Irrigation in Dry)",
            plantingGuide = "Raise seedlings in a nursery for 4 weeks before transplanting strong seedlings.",
            spacing = "60-75 cm between rows and 45-50 cm between plants.",
            fertilizer = "Apply basal NPK at transplanting. Top dress with Calcium Nitrate and potassium at flowering and fruiting stages.",
            weeding = "Regular shallow weeding. Stake indeterminate varieties for clean fruit and disease prevention.",
            harvesting = "Harvest when fruits reach firm ripe stage (turning red/orange)."
        ),
        CropGuide(
            id = "cabbage",
            name = "Cabbage",
            emoji = "🥬",
            category = "Vegetables",
            plantingSeason = "Cool season / Irrigation year-round",
            plantingGuide = "Sow seeds in a nursery bed for 4-5 weeks, then transplant sturdy seedlings into well-manured plots.",
            spacing = "45-60 cm between rows and 45 cm between plants.",
            fertilizer = "Apply compound fertilizer at planting and top-dress with nitrogen fertilizer at 3 and 6 weeks after transplanting.",
            weeding = "Shallow weeding is essential as cabbages have shallow root systems.",
            harvesting = "Harvest when heads are firm and compact when squeezed by hand."
        ),
        CropGuide(
            id = "carrots",
            name = "Carrots",
            emoji = "🥕",
            category = "Vegetables",
            plantingSeason = "Cool season / Irrigation",
            plantingGuide = "Plant seeds directly in loose, well-drained sandy loam soil at 1 cm depth. Avoid stony soils to prevent forked roots.",
            spacing = "20-30 cm between rows and 5 cm between plants (thin after germination).",
            fertilizer = "Apply well-rotted compost before planting. Avoid excess nitrogen which causes excessive leaf growth instead of roots.",
            weeding = "Keep weed-free especially when seedlings are young and slow-growing.",
            harvesting = "Harvest when roots reach desired size (2.5 to 4 months after planting) by pulling gently."
        ),
        CropGuide(
            id = "sorghum",
            name = "Sorghum",
            emoji = "🌾",
            category = "Cereals",
            plantingSeason = "Nov – Dec (Drought tolerant)",
            plantingGuide = "Plant shallow (2-3 cm) in rows with onset of rains. Highly resilient to dry spells.",
            spacing = "60-75 cm between rows and 15-20 cm between plants.",
            fertilizer = "Apply light top-dressing of Urea or NPK at 4 weeks after emergence.",
            weeding = "Weed early during the first 4 weeks.",
            harvesting = "Harvest when grain is hard and moisture is below 20%. Cut heads and sun dry."
        )
    )

    val calendarMonths = listOf(
        MonthInfo(
            monthNumber = 1,
            monthName = "January",
            periodTitle = "Rainy & Weeding Season",
            seasonType = "Rainy / Weeding",
            description = "Peak rainfall period. Crops like maize and beans are growing rapidly. Critical time for weeding and first top-dressing.",
            activities = listOf(
                "Apply top-dressing fertilizer (Urea) to maize",
                "Conduct second weeding to keep fields clean",
                "Monitor for pests (Fall Armyworm) and diseases"
            ),
            suitableCrops = listOf("Maize", "Beans", "Soybeans", "Sweet Potatoes")
        ),
        MonthInfo(
            monthNumber = 2,
            monthName = "February",
            periodTitle = "Rainy & Vegetative Growth",
            seasonType = "Rainy / Growth",
            description = "Vegetative growth continues. Legumes flowering and podding. Late planting of short-season crops.",
            activities = listOf(
                "Apply gypsum to groundnuts at flowering",
                "Ensure proper drainage in waterlogged fields",
                "Continue pest scouting"
            ),
            suitableCrops = listOf("Beans", "Soybeans", "Vegetables", "Sweet Potatoes")
        ),
        MonthInfo(
            monthNumber = 3,
            monthName = "March",
            periodTitle = "Late Rains & Maturation",
            seasonType = "Maturation",
            description = "Rains begin to taper off towards late March. Crops start grain filling and tuber enlargement.",
            activities = listOf(
                "Monitor maize grain filling stage",
                "Prepare storage granaries and bags",
                "Plan for late-season vegetable production"
            ),
            suitableCrops = listOf("Tomatoes", "Vegetables", "Sorghum")
        ),
        MonthInfo(
            monthNumber = 4,
            monthName = "April",
            periodTitle = "End of Rains & Drying",
            seasonType = "Harvest Prep",
            description = "End of rainy season. Crops begin drying in the field. Pre-harvest preparations.",
            activities = listOf(
                "Stop weeding as crops mature",
                "Inspect crop dryness in fields",
                "Prepare drying yards"
            ),
            suitableCrops = listOf("Tomatoes", "Vegetables")
        ),
        MonthInfo(
            monthNumber = 5,
            monthName = "May",
            periodTitle = "Harvest Season Begins",
            seasonType = "Harvest",
            description = "Main harvesting period for maize, beans, groundnuts, and soybeans. Cool dry weather begins.",
            activities = listOf(
                "Harvest matured maize, beans, and groundnuts",
                "Dry crops properly before storage",
                "Store grains in hermetic bags or treated silos"
            ),
            suitableCrops = listOf("Maize", "Beans", "Groundnuts", "Soybeans", "Sorghum")
        ),
        MonthInfo(
            monthNumber = 6,
            monthName = "June",
            periodTitle = "Harvesting & Threshing",
            seasonType = "Harvest",
            description = "Threshing of legumes and shelling of maize. Storing harvests safely.",
            activities = listOf(
                "Thresh beans and soybeans",
                "Shell maize and check moisture content",
                "Market surplus produce"
            ),
            suitableCrops = listOf("Maize", "Groundnuts", "Sorghum")
        ),
        MonthInfo(
            monthNumber = 7,
            monthName = "July",
            periodTitle = "Post-Harvest & Dry Season",
            seasonType = "Dry Season",
            description = "Post-harvest period. Livestock grazing on crop residues. Irrigation farming for winter crops.",
            activities = listOf(
                "Manage crop residues (avoid burning)",
                "Irrigate winter vegetables (dimba farming)",
                "Repair farm tools and equipment"
            ),
            suitableCrops = listOf("Tomatoes", "Cabbages", "Leafy Vegetables")
        ),
        MonthInfo(
            monthNumber = 8,
            monthName = "August",
            periodTitle = "Dry Season & Land Prep Planning",
            seasonType = "Dry Season",
            description = "Dry season. Time to plan for the next season, procure seeds and fertilizers.",
            activities = listOf(
                "Procure certified seeds and fertilizers early",
                "Plan crop rotation and field mapping",
                "Maintain irrigation systems"
            ),
            suitableCrops = listOf("Tomatoes", "Onions")
        ),
        MonthInfo(
            monthNumber = 9,
            monthName = "September",
            periodTitle = "Land Preparation (Early)",
            seasonType = "Land Prep",
            description = "Early land preparation, clearing fields, and making ridges or basins (conservation agriculture).",
            activities = listOf(
                "Clear fields and make manure application",
                "Construct ridges and swales for water harvesting",
                "Test soil if possible"
            ),
            suitableCrops = listOf("Vegetables (Irrigation)")
        ),
        MonthInfo(
            monthNumber = 10,
            monthName = "October",
            periodTitle = "Land Preparation & Pre-Rains",
            seasonType = "Land Prep",
            description = "Hot dry month before rains. Finalizing land preparation, manure incorporation, and seed selection.",
            activities = listOf(
                "Finalize ridge making and pitting",
                "Treat seeds with protectants",
                "Wait for reliable planting rains"
            ),
            suitableCrops = listOf("Seed selection & testing")
        ),
        MonthInfo(
            monthNumber = 11,
            monthName = "November",
            periodTitle = "Start of Rainy & Planting Season",
            seasonType = "Planting",
            description = "First rains arrive. Major planting period for maize, beans, groundnuts, and all rainfed crops.",
            activities = listOf(
                "Plant maize, beans, and groundnuts with first rains",
                "Apply basal fertilizer at planting",
                "Plant cassava and sweet potato vines"
            ),
            suitableCrops = listOf("Maize", "Beans", "Groundnuts", "Soybeans", "Cassava")
        ),
        MonthInfo(
            monthNumber = 12,
            monthName = "December",
            periodTitle = "Peak Planting & Germination",
            seasonType = "Planting",
            description = "Continuing planting and early weeding. Crop germination and early vegetative growth.",
            activities = listOf(
                "Complete late plantings",
                "Re-sow areas with poor germination (gapping)",
                "Perform first weeding"
            ),
            suitableCrops = listOf("Maize", "Beans", "Soybeans", "Sweet Potatoes")
        )
    )
}
