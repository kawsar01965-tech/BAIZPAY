package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.GlassBorderWhite10
import com.example.ui.theme.GlassWhite05
import com.example.ui.theme.GlassWhite10
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.TextMutedDark
import com.example.ui.theme.TextPrimaryDark

@Composable
fun AuthScreen(
    onLoginEmail: (emailOrUsername: String, pass: String) -> Unit,
    onRegisterEmail: (email: String, username: String, firstName: String, lastName: String, pass: String) -> Unit,
    onGoogleAuth: () -> Unit,
    onFacebookAuth: () -> Unit,
    onSkipDemo: () -> Unit
) {
    var isSignUpMode by remember { mutableStateOf(false) }

    // Form fields
    var emailInput by remember { mutableStateOf("") }
    var usernameInput by remember { mutableStateOf("") }
    var firstNameInput by remember { mutableStateOf("") }
    var lastNameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var confirmPasswordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // App Branding Header
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(MetallicGold, BrightGold)
                            )
                        )
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(DarkNavyCard),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "BP",
                        color = MetallicGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "BAIZPAY",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    color = TextPrimaryDark,
                    letterSpacing = 2.sp
                )

                Text(
                    text = "Global Multi-Currency Wallet & Earnings",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedDark,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        // Toggle Switch: Sign In vs Create Account
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(GlassWhite05)
                    .border(1.dp, GlassWhite10, RoundedCornerShape(20.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (!isSignUpMode) MetallicGold else Color.Transparent)
                        .clickable {
                            isSignUpMode = false
                            errorMessage = null
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SIGN IN",
                        color = if (!isSignUpMode) Color.Black else TextMutedDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSignUpMode) MetallicGold else Color.Transparent)
                        .clickable {
                            isSignUpMode = true
                            errorMessage = null
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CREATE ACCOUNT",
                        color = if (isSignUpMode) Color.Black else TextMutedDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // Error message card
        if (errorMessage != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF7F1D1D))
                        .padding(12.dp)
                ) {
                    Text(
                        text = errorMessage!!,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Form Card (Option 1: Email / Username / Names / Password)
        item {
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = 28.dp,
                borderColor = GlassBorderWhite10,
                contentPadding = 20.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = if (isSignUpMode) "OPTION 1: REGISTER WITH CREDENTIALS" else "OPTION 1: SIGN IN WITH CREDENTIALS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MetallicGold,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )

                    if (isSignUpMode) {
                        // First Name & Last Name
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = firstNameInput,
                                onValueChange = { firstNameInput = it },
                                label = { Text("First Name", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "First Name",
                                        tint = TextMutedDark,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MetallicGold,
                                    unfocusedBorderColor = GlassWhite10,
                                    focusedLabelColor = MetallicGold,
                                    unfocusedLabelColor = TextMutedDark,
                                    focusedTextColor = TextPrimaryDark,
                                    unfocusedTextColor = TextPrimaryDark
                                )
                            )

                            OutlinedTextField(
                                value = lastNameInput,
                                onValueChange = { lastNameInput = it },
                                label = { Text("Last Name", fontSize = 11.sp) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Badge,
                                        contentDescription = "Last Name",
                                        tint = TextMutedDark,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MetallicGold,
                                    unfocusedBorderColor = GlassWhite10,
                                    focusedLabelColor = MetallicGold,
                                    unfocusedLabelColor = TextMutedDark,
                                    focusedTextColor = TextPrimaryDark,
                                    unfocusedTextColor = TextPrimaryDark
                                )
                            )
                        }

                        // Username field
                        OutlinedTextField(
                            value = usernameInput,
                            onValueChange = { usernameInput = it },
                            label = { Text("Username", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Username",
                                    tint = TextMutedDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MetallicGold,
                                unfocusedBorderColor = GlassWhite10,
                                focusedLabelColor = MetallicGold,
                                unfocusedLabelColor = TextMutedDark,
                                focusedTextColor = TextPrimaryDark,
                                unfocusedTextColor = TextPrimaryDark
                            )
                        )
                    }

                    // Email Address field
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text(if (isSignUpMode) "Email Address" else "Email or Username", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = TextMutedDark,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MetallicGold,
                            unfocusedBorderColor = GlassWhite10,
                            focusedLabelColor = MetallicGold,
                            unfocusedLabelColor = TextMutedDark,
                            focusedTextColor = TextPrimaryDark,
                            unfocusedTextColor = TextPrimaryDark
                        )
                    )

                    // Password field
                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("Password", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = TextMutedDark,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Password",
                                    tint = TextMutedDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MetallicGold,
                            unfocusedBorderColor = GlassWhite10,
                            focusedLabelColor = MetallicGold,
                            unfocusedLabelColor = TextMutedDark,
                            focusedTextColor = TextPrimaryDark,
                            unfocusedTextColor = TextPrimaryDark
                        )
                    )

                    if (isSignUpMode) {
                        // Confirm Password field
                        OutlinedTextField(
                            value = confirmPasswordInput,
                            onValueChange = { confirmPasswordInput = it },
                            label = { Text("Confirm Password", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Confirm Password",
                                    tint = TextMutedDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MetallicGold,
                                unfocusedBorderColor = GlassWhite10,
                                focusedLabelColor = MetallicGold,
                                unfocusedLabelColor = TextMutedDark,
                                focusedTextColor = TextPrimaryDark,
                                unfocusedTextColor = TextPrimaryDark
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Submit Primary Action Button
                    Button(
                        onClick = {
                            if (isSignUpMode) {
                                when {
                                    emailInput.isBlank() || usernameInput.isBlank() || firstNameInput.isBlank() || lastNameInput.isBlank() || passwordInput.isBlank() -> {
                                        errorMessage = "Please fill in all mandatory fields."
                                    }
                                    passwordInput != confirmPasswordInput -> {
                                        errorMessage = "Passwords do not match."
                                    }
                                    else -> {
                                        errorMessage = null
                                        onRegisterEmail(
                                            emailInput.trim(),
                                            usernameInput.trim(),
                                            firstNameInput.trim(),
                                            lastNameInput.trim(),
                                            passwordInput
                                        )
                                    }
                                }
                            } else {
                                if (emailInput.isBlank() || passwordInput.isBlank()) {
                                    errorMessage = "Please enter your Email/Username and Password."
                                } else {
                                    errorMessage = null
                                    onLoginEmail(emailInput.trim(), passwordInput)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MetallicGold,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isSignUpMode) "CREATE ACCOUNT NOW" else "SIGN IN TO WALLET",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Submit",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section Divider
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = GlassWhite10
                )
                Text(
                    text = "  OR SOCIAL SIGN IN  ",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedDark,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = GlassWhite10
                )
            }
        }

        // Social Authentication Options (Option 2: Google, Option 3: Facebook)
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Option 2: Google Sign In
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(GlassWhite05)
                        .border(1.dp, GlassWhite10, RoundedCornerShape(20.dp))
                        .clickable { onGoogleAuth() }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Google "G" Badge Accent
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "G",
                                color = Color(0xFF4285F4),
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isSignUpMode) "OPTION 2: SIGN UP WITH GOOGLE" else "OPTION 2: SIGN IN WITH GOOGLE",
                            color = TextPrimaryDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Option 3: Facebook Sign In
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1877F2).copy(alpha = 0.2f))
                        .border(1.dp, Color(0xFF1877F2).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .clickable { onFacebookAuth() }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Facebook "f" Badge Accent
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1877F2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "f",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (isSignUpMode) "OPTION 3: SIGN UP WITH FACEBOOK" else "OPTION 3: SIGN IN WITH FACEBOOK",
                            color = TextPrimaryDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // Quick Guest Demo Access Button
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSkipDemo() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "⚡ Continue as Guest / Demo Mode",
                    color = TextMutedDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
