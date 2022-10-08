tasks.create<com.subpm.SubPM>("install") {
    actionType.set("install")
}

tasks.create<com.subpm.SubPM>("update") {
    actionType.set("update")
}

tasks.create<com.subpm.SubPM>("reset") {
    actionType.set("reset")
}