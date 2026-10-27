# Test Cases - Mlimi365 (Farm Season & Crop Guide Mobile Application)

Based on the project documentation and the provided test case template guidelines, the following test cases have been designed for the Mlimi365 Android application.

## Project Information
- **Student Name**: [Student Name]
- **Student ID**: [Student ID]
- **Application Name**: Mlimi365 (Farm Season & Crop Guide Mobile Application)
- **Target Platform**: Android 7.0 (API 24) to Android 15 (API 35+)
- **Date**: 10/04/2025
- **Template Version**: v1.0

---

## Test Cases

### Test Case 1: View January to December Farming Calendar
- **Test Case ID**: `CAL_CAL_001`
- **Description**: Verify that farmers can view the complete January to December farming calendar showing important farming periods.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App is installed and user has completed onboarding and reached the Home screen.
- **Test Data**: None
- **Test Steps**:
  1. Launch the application and navigate to the Home screen.
  2. Tap the "Calendar" icon in the bottom navigation bar.
  3. Scroll through the months from January to December.
- **Expected Result**: The calendar displays all 12 months with corresponding seasonal farming periods (e.g., Rainy Season, Harvest Season, Dry Season, Land Preparation) and key activities for each month.
- **Post-conditions**: User is on the Calendar screen viewing seasonal information.
- **Status**:

---

### Test Case 2: Calendar Navigation with Edge Cases
- **Test Case ID**: `CAL_CAL_002`
- **Description**: Verify that the calendar screen handles rapid scrolling and month data rendering without crashing or freezing.
- **Test Type**: Negative
- **Priority**: Low
- **Prerequisites**: App installed; user on Calendar screen.
- **Test Data**: Rapid vertical scroll gestures.
- **Test Steps**:
  1. Navigate to the Calendar screen.
  2. Rapidly scroll up and down across the January–December list multiple times.
- **Expected Result**: The UI remains responsive, scrolls smoothly, and correctly renders all month cards without application crashes or memory leaks.
- **Post-conditions**: Calendar screen remains stable and responsive.
- **Status**:

---

### Test Case 3: Identify Current Farming Period Based on Date
- **Test Case ID**: `CAL_CUR_001`
- **Description**: Verify that the application correctly identifies and displays the current farming period based on the device's current date.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App installed; device date set to a known month (e.g., January).
- **Test Data**: Device Date: January 15th.
- **Test Steps**:
  1. Open the application to the Home screen.
  2. Observe the "Current Period" card.
  3. Tap the "Current Period" card to view detailed period breakdown.
- **Expected Result**: The app correctly identifies the current period (e.g., Rainy Season for January) and displays the active month badge and seasonal summary on both the Home and Current Period screens.
- **Post-conditions**: User views the current farming period details.
- **Status**:

---

### Test Case 4: Current Period with Unsupported System Date
- **Test Case ID**: `CAL_CUR_002`
- **Description**: Verify that the application defaults safely when the system date is improperly configured or out of normal bounds.
- **Test Type**: Negative
- **Priority**: Medium
- **Prerequisites**: App installed.
- **Test Data**: Device Date: Invalid/Corrupted system date (e.g., year 1970).
- **Test Steps**:
  1. Set device date to an unhandled or corrupted date.
  2. Launch the application and view the Current Period card.
- **Expected Result**: The application falls back to a default active period gracefully without throwing an unhandled exception or crashing.
- **Post-conditions**: App displays fallback period information.
- **Status**:

---

### Test Case 5: Display Crops Suitable for Current Farming Period
- **Test Case ID**: `CROP_SUIT_001`
- **Description**: Verify that crops suitable for the current farming period are correctly displayed on the Home and Current Period screens.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App installed; user on Home or Current Period screen.
- **Test Data**: Current season mapping (e.g., Rainy Season crops: Maize, Beans, Groundnuts, Soybeans).
- **Test Steps**:
  1. View the "Recommended Crops" section on the Home screen.
  2. Tap the Current Period card to view the Current Period screen and its suitable crops list.
- **Expected Result**: Appropriate suitable crops (such as Maize, Beans, Groundnuts, Soybeans) are clearly displayed with their respective emojis and names.
- **Post-conditions**: Suitable crops are visible to the user.
- **Status**:

---

### Test Case 6: Empty Crop Mapping Handling
- **Test Case ID**: `CROP_SUIT_002`
- **Description**: Verify that the application displays a friendly placeholder message if a farming period has no associated crops.
- **Test Type**: Negative
- **Priority**: Low
- **Prerequisites**: App installed; mock period with zero crops.
- **Test Data**: None (Simulated empty crop list).
- **Test Steps**:
  1. Navigate to a calendar period with no suitable crops defined.
- **Expected Result**: The app handles the empty list gracefully without UI distortion or runtime crashes.
- **Post-conditions**: UI remains stable.
- **Status**:

---

### Test Case 7: Select Crop and View Basic Growing Guide
- **Test Case ID**: `CROP_GUIDE_001`
- **Description**: Verify that selecting a crop allows farmers to view its complete growing guide including planting, spacing, fertilizer, weeding, and harvesting.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App installed; user on Home, Search, or Calendar screen.
- **Test Data**: Crop Name: "Maize".
- **Test Steps**:
  1. Tap on "Maize" from the Recommended Crops or Search screen.
  2. Review the displayed growing guide.
- **Expected Result**: The Crop Detail screen opens displaying the crop name, emoji, category, planting season, and structured guidance on planting, spacing, fertilizer application, weeding, and harvesting.
- **Post-conditions**: User views the comprehensive crop growing guide.
- **Status**:

---

### Test Case 8: View Guide for Non-Existent Crop
- **Test Case ID**: `CROP_GUIDE_002`
- **Description**: Verify handling when trying to view a crop guide for an unlisted or non-existent crop.
- **Test Type**: Negative
- **Priority**: Medium
- **Prerequisites**: App installed.
- **Test Data**: Invalid Crop Name: "NonExistentCrop123".
- **Test Steps**:
  1. Navigate to Crop Detail screen with an invalid crop parameter.
- **Expected Result**: The application falls back to a default generic crop guide template rather than crashing or displaying a blank screen.
- **Post-conditions**: Fallback guide is displayed.
- **Status**:

---

### Test Case 9: View Reminders for Seasonal Farming Activities
- **Test Case ID**: `REM_REM_001`
- **Description**: Verify that reminders for important seasonal farming activities are displayed under upcoming and completed tabs.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App installed.
- **Test Data**: None
- **Test Steps**:
  1. Tap the "Reminders" icon in the bottom navigation bar.
  2. Switch between the "Upcoming" and "Completed" tabs.
- **Expected Result**: Reminders for seasonal activities (e.g., land preparation, weeding, fertilizing, harvesting) are correctly listed with dates and activity titles under the respective tabs.
- **Post-conditions**: User views seasonal reminders.
- **Status**:

---

### Test Case 10: Reminders Tab with No Items
- **Test Case ID**: `REM_REM_002`
- **Description**: Verify that selecting a reminder tab with no items displays an appropriate empty state message.
- **Test Type**: Negative
- **Priority**: Low
- **Prerequisites**: App installed; Reminders screen open.
- **Test Data**: Tab with zero items.
- **Test Steps**:
  1. Navigate to the Reminders screen.
  2. Select a tab category where all items are filtered out.
- **Expected Result**: An empty state message ("No reminders found") is displayed cleanly without UI errors.
- **Post-conditions**: Empty state is shown.
- **Status**:

---

### Test Case 11: Search for Crops and Access Growing Information
- **Test Case ID**: `SEARCH_SRCH_001`
- **Description**: Verify that farmers can search for crops using keywords and access their growing information.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App installed; user on Home screen.
- **Test Data**: Search Query: "Maize" (or clicking popular search tag "Beans").
- **Test Steps**:
  1. Tap the "Search" icon in the bottom navigation bar or tap "View Crop Guides".
  2. Type "Maize" into the search text field (or tap a popular search tag).
  3. Tap on the search result item.
- **Expected Result**: The list filters in real-time to show matching crops. Tapping the crop successfully navigates to its detailed growing guide.
- **Post-conditions**: User views the selected crop guide.
- **Status**:

---

### Test Case 12: Search for Non-Existent Crop
- **Test Case ID**: `SEARCH_SRCH_002`
- **Description**: Verify that searching for a crop that does not exist displays a "No crops found" message.
- **Test Type**: Negative
- **Priority**: Medium
- **Prerequisites**: App installed; user on Search screen.
- **Test Data**: Search Query: "XYZ999".
- **Test Steps**:
  1. Navigate to the Search screen.
  2. Enter an unmatching search query into the search text field.
- **Expected Result**: The app displays a friendly message: "No crops found matching \"XYZ999\"".
- **Post-conditions**: Search results list shows empty state message.
- **Status**:

---

### Test Case 13: Offline Accessibility of Main Farming Information
- **Test Case ID**: `OFF_OFF_001`
- **Description**: Verify that the main farming information including calendar, crop guides, and reminders is fully accessible offline without internet connectivity.
- **Test Type**: Positive
- **Priority**: High
- **Prerequisites**: App installed; device network connection (Wi-Fi and Mobile Data) disabled.
- **Test Data**: None (Offline mode).
- **Test Steps**:
  1. Disable Wi-Fi and Cellular Data on the test device.
  2. Launch the Mlimi365 application.
  3. Navigate through the Calendar, Search, Reminders, and Crop Guide screens.
- **Expected Result**: All farming calendars, seasonal periods, crop-growing guides, and reminders load successfully from local storage without requiring internet connectivity.
- **Post-conditions**: App functions fully offline.
- **Status**:
