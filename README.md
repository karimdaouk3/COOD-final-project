# Project Implementation Checklist

## Architecture Requirements

- [x] N-Tier Architecture
  - Data Management: `src/main/java/datamanagement/`
  - Processing: `src/main/java/menu/`
  - Common: `src/main/java/common/`
- [x] Package organization with tier-based naming
- [ ] Main class with main() method

## Design Patterns (At least 2 required)

- [x] Strategy Pattern
  - `FileParsingStrategy.java` (interface)
  - Implementations: `CSVParsingStrategy`, `TextParsingStrategy`, `JSONParsingStrategy`
  - Used by: `CSVPropertyValueReader`, `CSVParkingViolationReader`, `TxtPopulationReader`, `JSONParkingViolationReader`

- [x] Iterator Pattern
  - `ZipCodeFilteringIterator.java`
  - Used by: `MenuOption5Processor.calculateMarketValuePerCapita()` (lines 40-41, 49-50), `CalculationUtils.calculateAverage()` (line 18)

## Java Features (At least 2 required)

- [x] Generics
  - `CalculationUtils.calculateAverage()` (line 8): `public static <T> int calculateAverage(...)`
  - `ZipCodeFilteringIterator.java` (line 8): `public class ZipCodeFilteringIterator<T>`

- [x] Lambda Expressions
  - `MenuOption3Processor.java` (lines 35-36): `pv -> pv.getZipCode()`, `pv -> pv.getMarketValue()`
  - `MenuOption4Processor.java` (lines 23-24): `pv -> pv.getZipCode()`, `pv -> pv.getLivableArea()`
  - `MenuOption5Processor.java` (lines 41, 50): `pv -> pv.getZipCode()`, `pd -> pd.getZipCode()`

## Memoization

- [x] Memoization in Processing Tier
  - `MenuOption3Processor.calculateMarketValue()` (lines 23-40)
  - Uses `Map<String, Integer> marketValueCache` (line 12) to cache results by ZIP code

## Error Handling

- [x] Defensive programming (null checks, input validation)
- [x] Division by zero protection
- [x] File reading error handling (IOException)
- [x] Invalid data handling

## Functionality

- [x] Menu Option 1: Total Population (`MenuOption1Processor`)
- [x] Menu Option 2: Fines Per Capita (`MenuOption2Processor`)
- [x] Menu Option 3: Average Market Value (`MenuOption3Processor`)
- [x] Menu Option 4: Average Livable Area (`MenuOption4Processor`)
- [x] Menu Option 5: Market Value Per Capita (`MenuOption5Processor`)

## Data Management

- [x] `CSVParkingViolationReader.java`
- [x] `JSONParkingViolationReader.java`
- [x] `CSVPropertyValueReader.java`
- [x] `TxtPopulationReader.java`

## Testing

- [x] JUnit tests for all processor classes (47 tests, 0 failures)
  - `MenuOption1ProcessorCalculateTotalPopulationTest.java` (4 test cases)
  - `MenuOption2ProcessorCalculateFinesPerCapitaTest.java` (6 test cases)
  - `MenuOption3ProcessorCalculateMarketValueTest.java` (6 test cases)
  - `MenuOption4ProcessorCalculateTotalLivableAreaTest.java` (5 test cases)
  - `MenuOption5ProcessorCalculateMarketValuePerCapitaTest.java` (7 test cases)
- [x] Mock objects using anonymous inner classes
- [x] Test cases have 100% statement coverage for processor methods

## Remaining Work

- [ ] Main class implementation
  - Accept command-line arguments (format, parkingFile, propertyFile, populationFile)
  - Validate arguments and file existence
  - Display interactive menu
  - Handle user input (menu selection, ZIP code prompts)
  - Instantiate readers based on format argument
  - Instantiate processors
  - Display formatted results
  - Loop menu until user selects exit (0)
- [ ] Confirm test cases have 100% coverage
