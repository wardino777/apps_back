# AppTrabajo — Cliente Móvil (Pañol / MPAZ)

Aplicación móvil Android desarrollada en **Kotlin** y **Jetpack Compose** bajo arquitectura **MVVM**, integrada con **Firebase** (Auth + Firestore), **Retrofit** (consumo de microservicios REST en Spring Boot) y **recursos nativos** del dispositivo.

## Estructura del Proyecto
- `ui/`: Pantallas Compose (`LoginScreen`, `RegistroScreen`, `RecuperarPasswordScreen`, `MenuSolicitanteScreen`, `MisSolicitudesScreen`, `ConsolaAdminScreen`, `AgregarEquipoScreen`, `InventarioScreen`, `PerfilScreen`, `MenuPrincipalScreen`, `DirectorioScreen`).
- `viewmodel/`: Gestión de estado y lógica de negocio (`LoginViewModel`, `MenuSolicitanteViewModel`, `ConsolaAdminViewModel`, `MisSolicitudesViewModel`, `PerfilViewModel`, etc.).
- `data/model/`: Modelos de datos (`Usuario`, `Equipo`, `Ticket`, etc.) y configuración de red (`RetrofitClient`).
- `utils/`: Helpers nativos y sensores (detección de agitado con acelerómetro, notificaciones, vibración, etc.).

## Requisitos Previos
- **Android Studio** con SDK de Android instalado.
- **JDK 17** configurado en el proyecto.
- Proyecto de **Firebase** configurado con `google-services.json` en `app/` y proveedores de autenticación habilitados.
- Los 4 microservicios Spring Boot levantados en los puertos `8081`–`8084` (si se prueban flujos integrados con backend).

## Ejecución en Emulador o Dispositivo Físico
1. Abre el proyecto en **Android Studio** y espera a que sincronice Gradle.
2. Verifica que `AndroidManifest.xml` tenga `android:usesCleartextTraffic="true"` dentro de `<application>` para permitir tráfico HTTP local en desarrollo.
3. Si usas **Emulador Android**, las URLs locales apuntan a `http://10.0.2.2:<PUERTO>/`.
4. Si usas **celular físico por USB**, configura `adb reverse` en PowerShell apuntando al `platform-tools` del SDK:
   ```powershell
   $adb = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
   & $adb reverse tcp:8081 tcp:8081
   & $adb reverse tcp:8082 tcp:8082
   & $adb reverse tcp:8083 tcp:8083
   & $adb reverse tcp:8084 tcp:8084
