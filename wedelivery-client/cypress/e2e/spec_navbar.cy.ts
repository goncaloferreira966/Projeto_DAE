describe('Navbar', () => {
  beforeEach(() => {
    cy.visit('/manager'); // Visita a página de pedidos antes de cada teste
  });

  it('should render the navbar', () => {
    cy.get('nav').should('exist'); // Verifica se a navbar está presente
  });

  it('should have a link to Orders', () => {
    cy.get('a').contains('Orders').click(); // Clica no link Orders
    cy.url().should('include', '/manager'); // Verifica se a URL contém '/manager'
  });

  it('should have a link to E-Commerce', () => {
    cy.get('a').contains('E-Commerce').click(); // Clica no link E-Commerce
    cy.url().should('include', '/e-commerce'); // Verifica se a URL contém '/e-commerce'
  });

  it('should have a link to Clients', () => {
    cy.get('a').contains('Clients').click(); // Clica no link Clients
    cy.url().should('include', '/clients'); // Verifica se a URL contém '/clients'
  });
});
