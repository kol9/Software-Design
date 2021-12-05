//
//  PrintVisitor.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public class PrintVisitor: TokenVisitor {
    public func visit(_ token: ScalarToken) {
        switch token {
        case .intValue(let v):
            print("NUMBER(\(v))", terminator: " ")
        }
    }
    
    public func visit(_ token: ExtraToken) {
        switch token {
        case .leftPar:
            print("LP", terminator: " ")
        case .rightPar:
            print("RP", terminator: " ")
        }
    }
    
    public func visit(_ token: OperationToken) {
        switch token {
        case .add:
            print("ADD", terminator: " ")
        case .sub:
            print("SUB", terminator: " ")
        case .mul:
            print("MUL", terminator: " ")
        case .div:
            print("DIV", terminator: " ")
        }
    }
}
