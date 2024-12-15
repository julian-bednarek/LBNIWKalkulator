# Exposure Time Calculator

The **LBNIW Kalkulator** is an Android application designed for Non-Destructive Testing (NDT) firms to help technicians calculate exposure times for X-ray lamps or selected isotopes. It features a timer to track the exposure time, and sends notifications and alarms when the time is up. The app is built using Java and targets Android 13 (API level 33).

## Features

- **X-ray Lamp Time Calculation**: Calculates the exposure time based on the selected X-ray lamp and isotope.
- **Isotope Selection**: Choose from a variety of isotopes to adjust the exposure time calculation.
- **Exposure Timer**: Start and track exposure time with a countdown timer.
- **Notifications & Alarms**: Receive notifications and alarms when the exposure time is complete.
- **Offline Functionality**: No internet required to use the app, all data is locally stored.

## Requirements

- **Android Version**: Android 13 (API level 33) or higher.
- **Permissions**:
  - `VIBRATE`: To trigger vibration alerts on timer completion.
  - `NOTIFICATIONS`: To send alerts when the timer finishes.
 
## Limitations

- **Types of isotopes and lamps are manually added.** This can change in the future

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ndt-exposure-time-calculator.git
   ```
