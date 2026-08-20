# Database 
## User

- id
- Name
- Email
- Password 

## Portfolio 
- id
- user_id ( RelationShip one to many || optional )
- portfolio_name

## Holdings 

- id
- portfolio_id ( RelationShip one to many || compulsary)
- symbol
- quantity
- buy_price
- purchase_date