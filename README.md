# pruebaPkedex (Compose + MVVM + Hilt + Room + Paging 3)

Aplicación tipo **Pokédex** con:
- BottomBar (3 secciones): **Lista**, **Radar/Random**, **Favoritos**
- **Paginación de 25** con **RemoteMediator** y **cache offline** con Room
- **Detalle** (id, nombre, sprite/imágenes, altura, peso, tipos)
- **Favoritos** (toggle en lista y detalle)
- **Radar**: alerta “Pokémon encontrado” al detectar **10 m** (GPS) + **vibración**; botón manual **Random**

## 📸 Demo


https://github.com/user-attachments/assets/4eb03a0d-ee35-4a33-b29e-aadb19f863e5



---

## 🧱 Stack

- **Kotlin**, **Jetpack Compose (Material 3)**, **Navigation Compose**
- **Hilt** para inyección de dependencias
- **Retrofit + OkHttp + Moshi** (PokeAPI)
- **Room + Paging 3 (RemoteMediator)** para cache/paginación
- **Coil** para imágenes
- **Play Services Location** + **Accompanist Permissions** (Radar)

---

## 📂 Estructura (monomódulo)

app/
build.gradle.kts
src/main/
AndroidManifest.xml
java/com/example/pokedex/
PokedexApp.kt
di/...
data/...
domain/...
ui/...
theme/...
res/values/strings.xml
---

## 🚀 Requisitos y ejecución

- Android Studio **Ladybug+**
- JDK **17**
- `minSdk=24`, `target/compile=36`

Permisos en `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.VIBRATE"/>
Build & run:

🔌 API
PokeAPI pública (no requiere API key): https://pokeapi.co/

✨ Funcionalidades clave
Lista paginada (25 en 25) con RemoteMediator → Room es la fuente de verdad

Offline-first (si no hay red, se muestran datos cacheados)

Favoritos persistentes (toggle en lista y detalle)

Detalle con sprite/imágenes, altura, peso, tipos

Radar: alerta + vibración al detectar 10 m; botón manual Random

🏗️ Arquitectura
Clean Architecture (data/domain/presentation) + MVVM

Flujos con Kotlin Flow y PagingData

Hilt para DI

Detalle: botón para Agregar/Quitar de favoritos

Radar: al moverte 10 m aparece alerta y vibra. Botón Random siempre disponible.

⚠️ Errores y estados
Estados de carga/append/error gestionados con Paging LoadState

Reintentos con scroll/refresh (y manejo básico de excepciones en RemoteMediator/Repo)

🛡️ Licencia
MIT © Brandon Mora Anaya

