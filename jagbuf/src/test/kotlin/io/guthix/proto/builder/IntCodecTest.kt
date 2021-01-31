package io.guthix.proto.builder

class IntCodecTest : MessageDescriptionTest() {
    override val folder: String = "intcodec"

    init {
        "Single property test " {
            val generatedFile = object : MessageDescription() {
                init {
                    message(pkg = "io.guthix.proto", name = "Foo") {
                        val x by IntProperty()
                        codec {
                            x.asInt()
                        }
                    }
                }
            }.messages.first()
            doTest(generatedFile, "SingleProperty")
        }
    }

}