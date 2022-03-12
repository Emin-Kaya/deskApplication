package com.example.deskApplication.exception

class EntryNotFoundException(message: String, id: String = "unknown id") : Exception("$message: $id")

