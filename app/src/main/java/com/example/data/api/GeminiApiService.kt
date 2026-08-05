package com.example.data.api

import com.example.BuildConfig
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class Part(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class GenerationConfig(
    val temperature: Float? = 0.7f
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content? = null
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GeminiApiService::class.java)
    }
}

class GeminiFinancialAssistant {
    suspend fun getFinancialAdvice(
        totalBalance: Double,
        referralEarnings: Double,
        currentRank: String,
        userQuery: String
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try { BuildConfig.GEMINI_API_KEY } catch (e: Exception) { "" }
        if (apiKey.isBlank()) {
            return@withContext "💡 AI Assistant Tip: To maximize your referral income, focus on helping your Level 1 members verify their account with $5 USD. You'll earn $2.00 instantly and unlock commissions through Level 5!"
        }

        val prompt = """
            You are BaizPay AI, an elite international financial advisor and wallet strategy assistant.
            User Profile Context:
            - Total Balance: $$totalBalance
            - Referral Earnings: $$referralEarnings
            - Monthly Salary Rank: $currentRank
            User Question/Prompt: "$userQuery"

            Provide a concise, highly motivating 2-3 sentence strategic recommendation focused on earning growth, rank progression, or wallet safety. Keep tone luxury and professional.
        """.trimIndent()

        val request = GenerateContentRequest(
            contents = listOf(
                Content(parts = listOf(Part(text = prompt)))
            )
        )

        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "💡 AI Assistant Tip: Keep expanding your team to reach the next Salary Rank tier for guaranteed monthly payouts!"
        } catch (e: Exception) {
            "💡 AI Strategy Tip: Reaching the next referral tier increases your monthly salary and activates 5-level commission multipliers!"
        }
    }
}
