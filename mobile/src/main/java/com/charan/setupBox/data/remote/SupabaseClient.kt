package com.charan.setupBox.data.remote


import com.charan.setupBox.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object supabaseClient {
    val client= createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY

    ){
        install(Auth)
        install(Storage)
        install(Postgrest)
        install(Realtime)
    }
}