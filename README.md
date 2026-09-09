# Git Gud

Η εφαρμογή Git Gud αποτελεί εκπαιδευτικό λογισμικό για την εκμάθηση του Git Version Control System.

## Prerequisites

- Java SDK 25
- Maven 3.9 (ή το built-in mvnw script)
- SpringBoot 4.1.0
- Firebase SDK 9.10.0

## Ρύθμιση της Firebase

1. Δημιουργήστε ένα project στο [Firebase Console](https://console.firebase.google.com/).
2. Ενεργοποιήστε το: `Authentication -> Sign-in method -> Email/Password`
3. Δημιουργήστε μια Firestore Database
4. Στο `Project Settings -> Service Account -> Generate new private key`
5. Μετονομάστε το αρχείο αυτό σε `firebase-service-account.json`
6. Tοποθετήστε το μέσα στο `src/main/resources/firebase-service-account.json`

## Ρύθμιση του Firebase config

Το αρχείο `src/main/resources/static/js/firebase.js` περιέχει το public config του client SDK
(`apiKey`, `authDomain`, `projectId` etc.) Αντικαταστήστε τις τιμές με τις αντιστοιχες του δικού σας Firebase project.





## Αρχικοποίηση της Βάσης
Πριν την πρώτη εκκίνηση της εφαρμογής, τροποποιηστε προσωρινά το `src/main/resources/application.yaml`

```yaml
app:
  seeder:
    enabled: true
    reset: false
```
Στις επόμενες εκτελέσεις μπορείτε να το αλλάξετε σε `false`. Αν επιθυμείτε να κανετε reset την βάση, αλλάξτε και τις δύο τιμές σε true.

## Εκτελεση της εφαρμογής
Από το root του project 

### Linux
```shell
./mvnw spring-boot:run
```
### Windows
```shell
mvnw.cmd spring-boot:run
```

Η εφαρμογή θα ξεκινήσει στο
`http://localhost:8080`

## First Run

1. Ανοίξτε το `http://localhost:8080`
2. Πατήστε το **Register** για να δημιουργήσετε νέο λογαριασμό.
3. Μετά την εγγραφή, μεταφερθειτε στο `/home`, όπου έχετε προσβαση στα Tutorials, Statistics και Leaderboards.

## Πιθανά σφάλματα που μπορεί να προκύψουν:
1. Firebase service account file not found: το αρχείο `firebase-service-account.json` πρέπει να βρίσκετε ακριβώς στο `src/main/resources/`
2. `PERMISSION_DENIED`: η Firestore Database πρεπει να έχει δημιουργηθεί, και το service account να έχει τα απαραίτητα δικαιώματα. Ενδέχεται, επίσης, να χρειαστεί να διαγραψετε τα cookies.
3. Content not seeded: βεβαιωθείτε ότι το `app.seeder.enabled` ήταν `true` σε τουλάχιστον μία εκτέλεση


![Java](https://img.shields.io/badge/Java-25-hotpink)
![Maven](https://img.shields.io/badge/Maven-3.9-cyan)
![SpringBoot](https://img.shields.io/badge/Spring-4.1.0-green)
![Firebase SDK](https://img.shields.io/badge/Firebase-9.10.0-red)
