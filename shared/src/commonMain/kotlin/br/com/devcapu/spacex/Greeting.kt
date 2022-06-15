package br.com.devcapu.spacex

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}