package br.iesb.VIS2048.pca;

import Jama.Matrix;

public class Pca {
    
    private Matrix T;
    private Matrix L;
    
    public Pca(Matrix X, int nPcs) {
	int m = X.getRowDimension();
	int n = X.getColumnDimension();
	int A = nPcs;
	br.iesb.VIS2048.pca.SingularValueDecomposition svd = new br.iesb.VIS2048.pca.SingularValueDecomposition(X);
	Matrix S = svd.getS();
	T = svd.getU().getMatrix(0, m-1, 0,A-1).times(S.getMatrix(0, A-1, 0, A-1));
	L = svd.getV().getMatrix(0, n-1, 0, A-1);
    }

    public Matrix getT() {
	if (T == null) {
	    return null;
	} else {
	    return T.copy();
	}
    }

    public Matrix getL() {
	if (L == null) {
	    return null;
	} else {
	    return L.copy();
	}
    }
    
}
