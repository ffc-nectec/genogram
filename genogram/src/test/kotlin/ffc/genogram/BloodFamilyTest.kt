package ffc.genogram

/*@RunWith(Parameterized::class)
class BloodFamilyTest(private val resource: String, private val expectBloodFamily: List<Int>) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf("2ndGen/spouses/family-4-spouses-1.json",
                            listOf(0, 1, 2, 3, 4)),
                    arrayOf("3rdGen/family-5-children-3rd-gen-2.json",
                            listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)),
                    arrayOf("3rdGen/family-3-children-3rd-gen-6.json",
                            listOf(0, 1, 2, 3, 4, 5, 6, 7))
            )
        }
    }

    @Test
    fun bloodFamily() {
        val family = Family(1, "Smiths", getResourceAs<Family>(resource).members)

        family.bloodFamily!! `should equal` expectBloodFamily
    }

    @Test
    fun specifyRootPerson() {
        val members = getResourceAs<Family>(resource).members
        val disorder = members.sortedByDescending { it.idCard }
        disorder `should not equal` members

        val family = Family(1, "Smiths", disorder, rootPerson = members[0])

        family.bloodFamily!! `should equal` expectBloodFamily
    }
}*/
