# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

# Keep data classes
-keep class com.dailymotivation.data.** { *; }

# Keep widget classes
-keep class com.dailymotivation.widget.** { *; }

# Material Components
-keep class com.google.android.material.** { *; }

# AndroidX
-keep class androidx.** { *; }

# Kotlin
-keep class kotlin.** { *; }
-keepclassmembers class **$WhenMappings { <fields>; }
