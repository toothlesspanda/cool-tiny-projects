/***
 * move nD discos de uma torre origem para uma torre destino
 * usando uma torre temporaria temp
 */
void hanoi( int origem, int destino, int temp, int nD )
{
    if ( nD == 1 )
      printf ("%d -> %d\n", origem, destino);      
      /* imprime movimentos realizados */
 
    else {
      hanoi( origem, temp, destino, nD - 1 );
      printf( "%d -> %d\n", origem, destino );
      /* imprime movimentos realizados */
      hanoi( temp, destino, origem, nD - 1 );
   }
}