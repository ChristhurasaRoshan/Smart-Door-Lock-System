#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>

#define FIREBASE_HOST "sample-door-default-rtdb.firebaseio.com"
#define WIFI_SSID "Roshan"
#define WIFI_PASSWORD "roshan08@"

String fireStatus = ""; // LED status received from Firebase
int led = 5;

// Declare Firebase objects
FirebaseData firebaseData;
FirebaseConfig firebaseConfig;
FirebaseAuth firebaseAuth;

// Define Firebase authentication token
const char *FIREBASE_AUTH = "fd38f6058695774a9b826c8fff71f14773aab6e6";

void setup() {
  Serial.begin(115200);
  delay(1000);
  pinMode(led, OUTPUT);
  
  // Connect to WiFi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.println(WIFI_SSID);
  
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  
  Serial.println();
  Serial.println("WiFi connected");

  // Initialize Firebase configuration
  firebaseConfig.host = FIREBASE_HOST;

  // Set the authentication token
  firebaseAuth.token = firebase_auth_signin_token_t{FIREBASE_AUTH}; // Use curly braces to initialize the struct
  
  Firebase.begin(&firebaseConfig, &firebaseAuth);
  
    // Pass the FirebaseConfig and FirebaseAuth objects
  
  Firebase.reconnectWiFi(true); // Enable automatic reconnection to WiFi
  
  // Set initial LED status on Firebase
  if (Firebase.setString(firebaseData, "/currentStatus", "Close")) {
    Serial.println("Initial LED status set to Close on Firebase");
  } else {
    Serial.println("Failed to set initial LED status on Firebase");
  }  
}

void loop() {
  
  if (Firebase.getString(firebaseData, "/currentStatus")) {
    fireStatus = firebaseData.stringData();
    Serial.print("Firebase status: ");
    Serial.println(fireStatus);
  
    // Update LED based on Firebase status
    if (fireStatus == "Close") {
      Serial.println("Lock Turned ON");
      digitalWrite(led, HIGH);
    } else if (fireStatus == "Open") {
      Serial.println("Lock Turned OFF");
      digitalWrite(led, LOW);
    } else {
      Serial.println("Unknown status received from Firebase");
    }
  } else {
    Serial.println("Failed to get data from Firebase");
  }

  delay(10000); // Delay for stability
}
