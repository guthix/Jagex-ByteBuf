package io.guthix.proto.builder

class IntCodecTest : MessageDescriptionTest() {
    override val folder: String = "intcodec"

    init {
        "Single property test " {
            val name = "SingleProperty"
            val generatedFile = object : MessageDescription() {
                init {
                    message(pkg = folder, name = name) {
                        val x by IntProperty()
                        codec {
                            x.asInt()
                        }
                    }
                }
            }.messages.first()
            doTest(generatedFile, name)
        }

        "Multi property test " {
            val name = "MultiProperty"
            val generatedFile = object : MessageDescription() {
                init {
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
            }.messages.first()
            doTest(generatedFile, name)
        }

        "Short codec test " {
            val name = "ShortCodec"
            val generatedFile = object : MessageDescription() {
                init {
                    message(pkg = folder, name = name) {
                        val x by IntProperty()
                        codec {
                            x.asShort()
                        }
                    }
                }
            }.messages.first()
            doTest(generatedFile, name)
        }

        "Byte codec test " {
            val name = "ByteCodec"
            val generatedFile = object : MessageDescription() {
                init {
                    message(pkg = folder, name = name) {
                        val x by IntProperty()
                        codec {
                            x.asByte()
                        }
                    }
                }
            }.messages.first()
            doTest(generatedFile, name)
        }
    }

}