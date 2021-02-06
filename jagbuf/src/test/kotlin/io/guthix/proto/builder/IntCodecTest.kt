package io.guthix.proto.builder

class IntCodecTest : MessageDescriptionTest() {
    override val folder: String = "intcodec"

    init {
        "Single property test " {
            val name = "SingleProperty"
            doTest(name) {
                message(pkg = folder, name = name) {
                    val x by IntProperty()
                    codec {
                        x.asInt()
                    }
                }
            }
        }

        "Multi property test " {
            val name = "MultiProperty"
            doTest(name) {
                message(pkg = folder, name = name) {
                    val x by IntProperty()
                    val y by IntProperty()
                    val z by IntProperty()
                    codec {
                        x.asInt()
                        y.asInt()
                        z.asInt()
                    }
                }
            }
        }

        "Short codec test " {
            val name = "ShortCodec"
            doTest(name) {
                message(pkg = folder, name = name) {
                    val x by IntProperty()
                    codec {
                        x.asShort()
                    }
                }
            }
        }

        "Byte codec test " {
            val name = "ByteCodec"
            doTest(name) {
                message(pkg = folder, name = name) {
                    val x by IntProperty()
                    codec {
                        x.asByte()
                    }
                }
            }
        }
    }
}