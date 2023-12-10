
### Develop a backend application responsible for authorizing financial transactions based on predefined rules, user authentication, and account types.

#### Specific Rules to Implement:

* Transaction Amount Limit:
Scope Inclusion: Users can perform transactions up to $500 per day for regular accounts, $1000 per day for premium accounts.
Implementation: Validate incoming transaction amounts against the daily limit based on the user's account type.

* User Account Status Check:
Scope Inclusion: Transactions are allowed only for users with an 'active' account status.
Implementation: Verify the status of the user's account before authorizing any transaction request.

* Transaction Frequency Limit:
Scope Inclusion: Users can perform a maximum of 10 transactions per hour for regular accounts, 20 transactions per hour for premium accounts.
Implementation: Track and limit the number of transactions within an hourly window based on the user's account type.

* Transaction Balance Check:
Scope Inclusion: Authorize transactions only if the user's account balance covers the transaction amount.
Implementation: Check the user's account balance before authorizing the transaction, considering the account type.

* Transaction Time Window:
Scope Inclusion: Authorize transactions only during weekdays from 9 am to 5 pm for regular accounts, from 8 am to 8 pm for premium accounts.
Implementation: Restrict transaction authorization based on the current date and time against the specified time window and account type.

* Transaction Size Limit:
Scope Inclusion: Limit the size of individual transactions to $200 maximum for regular accounts, $500 maximum for premium accounts.
Implementation: Validate incoming transactions to ensure they do not exceed the predefined size limit based on the user's account type.

Unit Tests: Develop unit tests to verify the functionality and accuracy of the implemented authorization rules.
Scenario Tests: Include scenarios representing various account types and rule combinations to validate the system's behavior in diverse scenarios.
