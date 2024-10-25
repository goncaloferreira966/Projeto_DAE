describe('template spec', () => {
  
  beforeEach(() => {
    cy.visit("/"); // Visite a rota inicial antes de cada teste
  });

  it('passes', () => {
    // Confirme se a página foi carregada, verificando se o <nav> existe
    cy.get('nav').should('exist');
  });
  
  it('should have a nav bar with 4 links', () => {
    // Verifique a existência dos links após o carregamento da página
    cy.get('nav').contains('Home').should('exist');
    cy.get('nav').contains('Orders').should('exist');
    cy.get('nav').contains('E-Commerce').should('exist');
    cy.get('nav').contains('Clients').should('exist');
  });
  
  it('should navigate to the Orders page', () => {
    // Clique no link Orders e verifique se a URL foi alterada
    cy.get('nav').contains('Orders').click();
    cy.url().should('include', '/manager');
  });

  it('should have a title and specific card on the Orders page', () => {
      // Visite diretamente a página de Orders
      cy.visit("/manager");
      
      // Verifique se a página Orders contém o título
      cy.get('h2').contains('WeDelivery - Orders').should('exist');
      
      // Verifique se o card específico existe e contém o texto "View Details"
      cy.get('.card-body#0').should('exist').within(() => {
      // Verifique se o texto "View Details" existe no card
      cy.contains('View Details').should('exist');

      // Clique no botão "View Details" e verifique se a URL foi alterada
      cy.contains('View Details').click();
      //cy.url().should('match', /\/manager\/\d+$/);
    });
  });

});
